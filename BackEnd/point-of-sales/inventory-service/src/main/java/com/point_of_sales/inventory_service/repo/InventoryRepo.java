package com.point_of_sales.inventory_service.repo;

import com.point_of_sales.inventory_service.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {

    @Query(value =
            "SELECT i FROM Inventory i " +
            "WHERE (:inventoryName IS NULL OR i.name LIKE %:inventoryName% " +
            "OR i.description LIKE %:inventoryName%) " +
            "AND (:brands IS NULL OR i.brand.brandId IN :brands)")
    Page<Inventory> searchInventory(
            @Param("inventoryName") String inventoryName,
            @Param("brands") List<Integer> brands,
            Pageable pageable
    );

    @Query(value =
            "SELECT COUNT(*) FROM Inventory i " +
            "WHERE (:inventoryName IS NULL OR i.name LIKE %:inventoryName% " +
            "OR i.description LIKE %:inventoryName%) " +
            "AND (:brands IS NULL OR i.brand.brandId IN :brands)")
    long countInventory(
            @Param("inventoryName") String inventoryName,
            @Param("brands") List<Integer> brands
    );
}
