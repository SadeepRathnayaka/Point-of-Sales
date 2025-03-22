package com.point_of_sales.customer_service.service;

import com.point_of_sales.customer_service.dto.request.ReqCustomerDTO;
import com.point_of_sales.customer_service.dto.response.CustomerDTO;
import com.point_of_sales.customer_service.utils.StandardResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    CustomerDTO getCustomer(String phoneNumber);

    ResponseEntity<StandardResponse> addCustomer(ReqCustomerDTO reqCustomerDTO);

    ResponseEntity<StandardResponse> updatePoints(int customerId, int points);

    float claimPoints(int customerId);

    CustomerDTO getCustomerById(int customerId);
}
