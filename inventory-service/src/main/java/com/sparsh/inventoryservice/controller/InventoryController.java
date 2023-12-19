package com.sparsh.inventoryservice.controller;

import com.sparsh.inventoryservice.service.InventoryService;
import dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("skuCode") List<String> skuCode){
        log.info("checking stocks for : {}",skuCode);
        return inventoryService.isInStock(skuCode);
    }
}
