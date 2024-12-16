package com.microservice.ecommerce.customer;

import ch.qos.logback.core.util.StringUtil;
import com.microservice.ecommerce.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();


    }

    public void updateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Cannot update customer:: No customer found with the provided ID:: %s", request.id())));
        mergerCustomer(customer, request);
        repository.save(customer);
    }
    public List<CustomerResponse> getAllCustomers() {
        return repository.findAll().stream().map(mapper::fromCustomer).collect(Collectors.toList());
    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if(StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if(StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }

    }

    public Customer getCustomer(String id) {
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("Cannot update customer:: No customer found with the provided ID:: %s", id)));
    }

    public Boolean existsById(String id) {
        return repository.findById(id).isPresent();
    }

    public void deleteCustomer(String id) {
        repository.deleteById(id);
    }
}
