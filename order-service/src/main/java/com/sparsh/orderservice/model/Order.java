package com.sparsh.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.yaml.snakeyaml.tokens.Token;

import java.util.List;

@Entity
@Table(name = "t_order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItemList;
}
