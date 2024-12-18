package com.microservice.ecommerce.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class BussinessException extends RuntimeException {
    private final String msg;
}
