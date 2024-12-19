package com.microservice.ecommerce.order;

import com.microservice.ecommerce.customer.CustomerClient;
import com.microservice.ecommerce.exceptions.BussinessException;
import com.microservice.ecommerce.kafka.OrderConfirmation;
import com.microservice.ecommerce.kafka.OrderProducer;
import com.microservice.ecommerce.orderline.OrderLineRequest;
import com.microservice.ecommerce.orderline.OrderLineService;
import com.microservice.ecommerce.product.ProductClient;
import com.microservice.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    public Integer createdOrder(OrderRequest orderRequest) {
        //1). Check the customer
        var customer = customerClient.findCustomerById(orderRequest.customerId()).orElseThrow(() -> new BussinessException("Cannot create order:: No customer exists with the provided id::" + orderRequest.customerId()));
        //2). Purchase the products => product service
        var purchasedProducts = productClient.purchaseProducts(orderRequest.products());
        //3). Persist order
        var order = orderRepository.save(orderMapper.toOrder(orderRequest));
        //4). Persist order lines
        for(PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null, //=> id of the order line is null
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )

            );
        }
        //5). Start the Payment process

        //6). Send the order confirmation => Notification service (Kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::fromOrder).toList();
    }

    public OrderResponse findById(Integer id) {
        return orderMapper.fromOrder((orderRepository.findById(id)).orElseThrow(() -> new EntityNotFoundException("No order found with the provided id::" + id)));
    }
}
