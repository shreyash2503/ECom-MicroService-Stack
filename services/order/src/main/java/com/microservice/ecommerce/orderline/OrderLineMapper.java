package com.microservice.ecommerce.orderline;

import com.microservice.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine
                .builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .productId(orderLineRequest.productId())
                .order(Order.builder().id(orderLineRequest.orderId()).build())
                .build();

    }
}
