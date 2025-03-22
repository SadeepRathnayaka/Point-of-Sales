package com.point_of_sales.inventory_service.service.impl;

import com.point_of_sales.inventory_service.dto.paginated.PaginatedInventoryDTO;
import com.point_of_sales.inventory_service.dto.request.ReqAddInventoryDTO;
import com.point_of_sales.inventory_service.dto.response.ResInventoryDTO;
import com.point_of_sales.inventory_service.entity.Inventory;
import com.point_of_sales.inventory_service.exception.NotEnoughStockException;
import com.point_of_sales.inventory_service.exception.NotFoundException;
import com.point_of_sales.inventory_service.exception.PermissionDeniedException;
import com.point_of_sales.inventory_service.repo.BrandRepo;
import com.point_of_sales.inventory_service.repo.InventoryRepo;
import com.point_of_sales.inventory_service.service.InventoryService;
import com.point_of_sales.inventory_service.utils.Mappers.InventoryMapper;
import com.point_of_sales.inventory_service.utils.StandardResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceIMPL implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public ResponseEntity<StandardResponse> addInventory(ReqAddInventoryDTO reqAddInventoryDTO, String role) {

        if (role.equals("CASHIER")){throw new PermissionDeniedException("Permission Denied !");}
        Inventory inventory = modelMapper.map(reqAddInventoryDTO, Inventory.class);

        if (!brandRepo.existsById(reqAddInventoryDTO.getBrand_id())){
            throw new NotFoundException("Brand Not Found !");
        }

        inventory.setBrand(brandRepo.getReferenceById(reqAddInventoryDTO.getBrand_id()));
        inventoryRepo.save(inventory);
        return ResponseEntity.ok(new StandardResponse(200, "OK", "Inventory Added Successfully !"));
    }


    @Override
    public ResponseEntity<StandardResponse> updateStock(int inventoryId, int amount, int sign) {

        if (!inventoryRepo.existsById(inventoryId)){throw new NotFoundException("Inventory Not Found !");}
        Inventory inventory = inventoryRepo.getReferenceById(inventoryId);

        if (sign == 1){
            inventory.setStock(inventory.getStock() + amount);
        }else {
            if (inventory.getStock() < amount){
                throw new NotEnoughStockException("Not Enough Stock Available !");
            }
            inventory.setStock(inventory.getStock() - amount);
        }
        inventoryRepo.save(inventory);
        return ResponseEntity.ok(new StandardResponse(200, "OK", "Inventory Updated Successfully !"));
    }


    @Override
    public ResInventoryDTO getInventory(int inventoryId) {

        if (!inventoryRepo.existsById(inventoryId)){throw new NotFoundException("Inventory Not Found !");}
        Inventory inventory = inventoryRepo.getReferenceById(inventoryId);
        return inventoryMapper.InventoryToInventoryDto(inventory);
    }


    @Override
    public ResponseEntity<StandardResponse> getAllInventory(int page, int size) {
        Page<Inventory> inventories = inventoryRepo.findAll(PageRequest.of(page-1, size));
        List<ResInventoryDTO> inventoryDTOList = inventoryMapper.InventoryPageToInventoryDtoLis(inventories);
        long count = inventoryRepo.count();

        if (count > 0){
            PaginatedInventoryDTO paginatedInventoryDTO = new PaginatedInventoryDTO(inventoryDTOList, count);
            return ResponseEntity.ok(new StandardResponse(200, "OK", paginatedInventoryDTO));
        }
        throw new NotFoundException("Inventory Not Found !");
    }

    @Override
    public ResponseEntity<StandardResponse> searchInventory(int page, int size, String inventoryName, List<Integer> brands) {
        Page<Inventory> inventories = inventoryRepo.searchInventory(inventoryName, brands, PageRequest.of(page-1, size));
        List<ResInventoryDTO> inventoryDTOList = inventoryMapper.InventoryPageToInventoryDtoLis(inventories);
        long count = inventoryRepo.countInventory(inventoryName, brands);

        if (count > 0){
            PaginatedInventoryDTO paginatedInventoryDTO = new PaginatedInventoryDTO(inventoryDTOList, count);
            return ResponseEntity.ok(new StandardResponse(200, "OK", paginatedInventoryDTO));
        }
        throw new NotFoundException("Inventory Not Found !");
    }

    @Override
    public ResponseEntity<StandardResponse> deleteInventory(int inventoryId) {

        if (!inventoryRepo.existsById(inventoryId)){throw new NotFoundException("Inventory Not Found !");}
        Inventory inventory = inventoryRepo.getReferenceById(inventoryId);
        inventoryRepo.delete(inventory);

        return ResponseEntity.ok(new StandardResponse(200, "OK", "Inventory Deleted Successfully !"));
    }


}
