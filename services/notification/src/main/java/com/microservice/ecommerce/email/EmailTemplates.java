package com.microservice.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment_confirmation.html", "Payment Successfully processed"),
    ORDER_CONFIRMATION("order_confirmation.html", "Order confirmation");

    @Getter
    private final String template;

    @Getter
    private String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }



}
