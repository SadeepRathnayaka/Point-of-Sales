package com.point_of_sales.inventory_service.repo;

import com.point_of_sales.inventory_service.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BrandRepo extends JpaRepository<Brand, Integer> {
}
