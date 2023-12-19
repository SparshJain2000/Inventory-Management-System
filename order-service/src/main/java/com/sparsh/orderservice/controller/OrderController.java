package com.sparsh.orderservice.controller;

import com.sparsh.orderservice.dto.OrderRequest;
import com.sparsh.orderservice.model.Order;
import com.sparsh.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> createOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.createOrder(orderRequest));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        log.info(runtimeException.getMessage());
        return CompletableFuture.supplyAsync(() -> "Something went wrong! Please order after sometime");
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> listOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderById(@PathVariable String orderNumber) {
        Optional<Order> order = orderService.getOrderByOrderNumber(orderNumber);
        if (order.isPresent())
            return order.get();
        throw new ErrorResponseException(HttpStatus.NOT_FOUND);
    }

}
