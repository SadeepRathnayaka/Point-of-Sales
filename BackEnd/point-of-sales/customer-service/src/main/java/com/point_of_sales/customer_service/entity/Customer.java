package com.point_of_sales.customer_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(name = "name", nullable = false)
    private String customerName;

    @Column(name = "email", nullable = true, unique = true)
    private String customerEmail;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "loyalty_points", nullable = false)
    private int loyaltyPoints;
}
