package com.microservice.ecommerce.kafka;

import com.microservice.ecommerce.customer.CustomerResponse;
import com.microservice.ecommerce.order.PaymentMethod;
import com.microservice.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products



) {
}
