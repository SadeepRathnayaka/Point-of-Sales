package com.point_of_sales.inventory_service.utils.Mappers;

import com.point_of_sales.inventory_service.dto.response.ResInventoryDTO;
import com.point_of_sales.inventory_service.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "brand.brandName", target = "brandName")
    ResInventoryDTO InventoryToInventoryDto(Inventory inventory);
    List<ResInventoryDTO> InventoryPageToInventoryDtoLis(Page<Inventory> inventories);

}
