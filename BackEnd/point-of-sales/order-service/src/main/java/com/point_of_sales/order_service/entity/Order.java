package com.point_of_sales.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int oderId;

    @Column(name = "customer_id", nullable = true)
    private Integer customerId;

    @Column(name = "order_date", columnDefinition = "DATETIME", nullable = false )
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetailsList;

    @Column(name = "total", nullable = true)
    private float total;

}
