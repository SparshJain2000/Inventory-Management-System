package com.sparsh.orderservice.service;

import com.sparsh.orderservice.dto.InventoryResponse;
import com.sparsh.orderservice.dto.OrderLineItemDto;
import com.sparsh.orderservice.dto.OrderRequest;
import com.sparsh.orderservice.event.OrderPlacedEvent;
import com.sparsh.orderservice.model.Order;
import com.sparsh.orderservice.model.OrderLineItem;
import com.sparsh.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemList(orderRequest.getOrderLineItemDtoList().stream().map(this::mapFromDto).toList())
                .build();

        List<String> skuCodes = order.getOrderLineItemList().stream().map(OrderLineItem::getSkuCode).toList();
        //check if order in inventory
        List<InventoryResponse> isInStock = webClient.build().get().uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()
                )
                .retrieve()
//                .bodyToMono(InventoryResponse[].class)
                .bodyToMono(new ParameterizedTypeReference<List<InventoryResponse>>() {
                })
                .doOnError(e -> log.error(e.getMessage()))
                .block();

        assert isInStock != null;
        boolean allInStock = isInStock.stream().allMatch(InventoryResponse::getIsInStock);
        if (allInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            log.info("Order {} created", order.getId());
            return "Order Placed Successfully!";
        } else throw new IllegalArgumentException("Product is not in stock");
    }

    public List<Order> getOrders() {
        return orderRepository.findAll().stream().toList();
    }

    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumberIs(orderNumber).stream().findFirst();
    }

    private OrderLineItem mapFromDto(OrderLineItemDto orderLineItemDto) {
        return OrderLineItem.builder()
                .price(orderLineItemDto.getPrice())
                .skuCode(orderLineItemDto.getSkuCode())
                .quantity(orderLineItemDto.getQuantity())
                .build();
    }
}
