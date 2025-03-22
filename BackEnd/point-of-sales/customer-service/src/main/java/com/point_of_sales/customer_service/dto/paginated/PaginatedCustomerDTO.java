package com.point_of_sales.customer_service.dto.paginated;

import com.point_of_sales.customer_service.dto.response.CustomerDTO;
import com.point_of_sales.customer_service.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedCustomerDTO {

    private List<CustomerDTO> customers;
    private long count;
}
