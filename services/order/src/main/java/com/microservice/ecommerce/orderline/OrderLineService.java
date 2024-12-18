package com.microservice.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineMapper orderLineMapper;
    private final OrderLineRepository orderLineRepository;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        OrderLine orderLine = orderLineMapper.toOrderLine(orderLineRequest);
        // save orderLine
        return orderLineRepository.save(orderLine).getId();
    }
}
