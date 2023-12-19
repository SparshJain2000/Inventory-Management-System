package com.sparsh.inventoryservice;

import com.sparsh.inventoryservice.model.Inventory;
import com.sparsh.inventoryservice.repository.InventoryRepository;
import com.sparsh.inventoryservice.service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            if (inventoryRepository.findBySkuCode("iphone_13").isEmpty()) {
                Inventory inventory = Inventory.builder()
                        .skuCode("iphone_13")
                        .quantity(15)
                        .build();
                inventoryRepository.save(inventory);
            }
            if (inventoryRepository.findBySkuCode("iphone_14").isEmpty()) {
                Inventory inventory2 = Inventory.builder()
                        .skuCode("iphone_14")
                        .quantity(0)
                        .build();
                inventoryRepository.save(inventory2);
            }
        };
    }

}
