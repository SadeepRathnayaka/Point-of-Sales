package com.point_of_sales.inventory_service.service;

import com.point_of_sales.inventory_service.dto.request.ReqAddInventoryDTO;
import com.point_of_sales.inventory_service.dto.response.ResInventoryDTO;
import com.point_of_sales.inventory_service.utils.StandardResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {
    ResponseEntity<StandardResponse> addInventory(ReqAddInventoryDTO reqAddInventoryDTO, String role);

    ResponseEntity<StandardResponse> updateStock(int inventoryId, int amount, int sign);

    ResInventoryDTO getInventory(int inventoryId);

    ResponseEntity<StandardResponse> getAllInventory(int page, int size);

    ResponseEntity<StandardResponse> searchInventory(int page, int size, String inventoryName, List<Integer> brands);

    ResponseEntity<StandardResponse> deleteInventory(int inventoryId);
}
