package com.point_of_sales.customer_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqCustomerDTO {

    private String customerName;
    private String customerEmail;
    private String phoneNumber;
}
