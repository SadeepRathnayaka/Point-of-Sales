package com.point_of_sales.inventory_service.dto.request;

import com.point_of_sales.inventory_service.entity.Brand;
import com.point_of_sales.inventory_service.entity.Category;
import com.point_of_sales.inventory_service.entity.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqAddInventoryDTO {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private int brand_id;

    @NotBlank
    private Category category;

    @NotBlank
    private Unit unit;

    @NotNull
    private int stock;

    @NotNull
    private float inventoryPrice;

    private LocalDate inventoryExpireDate;
}
