package com.point_of_sales.inventory_service.dto.response;

import com.point_of_sales.inventory_service.entity.Category;
import com.point_of_sales.inventory_service.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResInventoryDTO {


    private int inventoryId;
    private String name;
    private String description;
    private String brandName;
    private Category category;
    private Unit unit;
    private int stock;
    private float inventoryPrice;
    private LocalDate inventoryExpireDate;
}
