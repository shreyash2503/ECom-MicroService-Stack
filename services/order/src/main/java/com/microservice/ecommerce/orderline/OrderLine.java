package com.microservice.ecommerce.orderline;

import com.microservice.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OrderLine {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    // Reference not added as the product may reside in a different database
    // Check the Domain Driven architecture in notes
    private Integer productId;
    private double quantity;


}
