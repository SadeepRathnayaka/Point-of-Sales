package com.point_of_sales.inventory_service.controller;

import com.point_of_sales.inventory_service.dto.request.ReqAddInventoryDTO;
import com.point_of_sales.inventory_service.dto.response.ResInventoryDTO;
import com.point_of_sales.inventory_service.service.InventoryService;
import com.point_of_sales.inventory_service.utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping(path = "/add-inventory")
    public ResponseEntity<StandardResponse> addInventory(@RequestBody ReqAddInventoryDTO reqAddInventoryDTO, @RequestHeader("Role") String role){
        return inventoryService.addInventory(reqAddInventoryDTO, role);
    }

    @GetMapping(path = "/get-inventory/{id}")
    public ResInventoryDTO getInventory(@PathVariable("id") int inventoryId){
        return inventoryService.getInventory(inventoryId);
    }

    @GetMapping(path = "/get-all-inventory")
    public ResponseEntity<StandardResponse> getAllInventory(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return inventoryService.getAllInventory(page, size);
    }

    @PutMapping(path = "/update-stock")
    public ResponseEntity<StandardResponse> updateStock(
            @RequestParam("id") int inventoryId,
            @RequestParam("amount") int amount,
            @RequestParam("sign") int sign
    ){
        return inventoryService.updateStock(inventoryId, amount, sign);
    }

    @GetMapping(path = "/search-inventory")
    public ResponseEntity<StandardResponse> searchInventory(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "inventoryName", required = false) String inventoryName,
            @RequestParam(value = "brands", required = false) List<Integer> brands
    ){
        return inventoryService.searchInventory(page, size, inventoryName, brands);
    }

    @DeleteMapping(path = "delete-inventory")
    public ResponseEntity<StandardResponse> deleteInventory(@RequestParam(value = "inventoryId") int inventoryId){
        return inventoryService.deleteInventory(inventoryId);
    }
}
