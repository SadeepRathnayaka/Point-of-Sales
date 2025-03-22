package com.point_of_sales.order_service.dto.request;

import com.point_of_sales.order_service.entity.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqOrderDTO {

    private String customerPhoneNumber;
    private LocalDateTime orderDate;
    private List<ReqOrderDetailsDTO> orderDetailsList;

}
