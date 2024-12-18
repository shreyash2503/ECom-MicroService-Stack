package com.microservice.ecommerce.order;

import com.microservice.ecommerce.customer.CustomerClient;
import com.microservice.ecommerce.exceptions.BussinessException;
import com.microservice.ecommerce.orderline.OrderLineRequest;
import com.microservice.ecommerce.orderline.OrderLineService;
import com.microservice.ecommerce.product.ProductClient;
import com.microservice.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    public Integer createdOrder(OrderRequest orderRequest) {
        //1). Check the customer
        var customer = customerClient.findCustomerById(orderRequest.customerId()).orElseThrow(() -> new BussinessException("Cannot create order:: No customer exists with the provided id::" + orderRequest.customerId()));
        //2). Purchase the products => product service
        productClient.purchaseProducts(orderRequest.products());
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
        return null;
    }
}
