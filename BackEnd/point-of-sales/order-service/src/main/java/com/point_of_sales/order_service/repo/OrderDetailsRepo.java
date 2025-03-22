package com.point_of_sales.order_service.repo;

import com.point_of_sales.order_service.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Integer> {
}
