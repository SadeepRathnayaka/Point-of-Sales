package com.point_of_sales.customer_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {

    private Integer customerId;
    private String customerName;
    private String customerEmail;
    private String phoneNumber;
    private int loyaltyPoints;
}
