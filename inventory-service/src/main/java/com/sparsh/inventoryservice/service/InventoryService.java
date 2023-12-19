package com.sparsh.inventoryservice.service;

import com.sparsh.inventoryservice.repository.InventoryRepository;
import dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
//        List<InventoryResponse> requestedItem = skuCode.stream().map(sku ->
//                InventoryResponse.builder().skuCode(sku).isInStock(false).build()
//        ).toList();
        Boolean inInventory = skuCodes.stream().allMatch(skuCode->inventoryRepository.findBySkuCode(skuCode).isPresent()) ;
        if(!inInventory) throw new IllegalArgumentException("Item not present in inventory");
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream().map(inventory ->
                InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()
        ).toList();
    }
}
