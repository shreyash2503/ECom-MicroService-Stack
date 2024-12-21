package com.microservice.ecommerce.payment;

import com.microservice.ecommerce.customer.CustomerResponse;
import com.microservice.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
