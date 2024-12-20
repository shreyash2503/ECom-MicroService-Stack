package com.microservice.ecommerce.orderline;

public record OrderLineResponse(
        Integer id,
        double quantity
) {
}
