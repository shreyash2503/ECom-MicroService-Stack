package com.microservice.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        customerService.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        var customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") String id) {
        var customer = customerService.getCustomer(id);
        return ResponseEntity.ok(customerMapper.fromCustomer(customer));
    }

    @GetMapping(path = "/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("id") String id) {
        return ResponseEntity.ok(customerService.existsById(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.accepted().build();

    }


}
