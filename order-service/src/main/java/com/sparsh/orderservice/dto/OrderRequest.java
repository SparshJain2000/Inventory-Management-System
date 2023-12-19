package com.sparsh.orderservice.dto;

import com.sparsh.orderservice.model.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {
    private long id;
    private List<OrderLineItemDto> orderLineItemDtoList;
}
