package com.point_of_sales.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "brand")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Brand {

    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandId;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private List<Inventory> inventoryList;
}
