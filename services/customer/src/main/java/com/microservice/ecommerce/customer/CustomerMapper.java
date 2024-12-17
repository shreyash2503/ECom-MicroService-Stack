package com.microservice.ecommerce.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request) {
        if(request == null) {
            return null;
        }
        System.out.println(request.email());
        return Customer.builder()
                        .id(request.id())
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .address(request.address())
                        .email(request.email())
                        .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
