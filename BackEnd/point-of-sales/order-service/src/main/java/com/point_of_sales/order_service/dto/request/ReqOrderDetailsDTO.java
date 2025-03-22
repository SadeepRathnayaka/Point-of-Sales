package com.point_of_sales.order_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqOrderDetailsDTO {

    private int inventoryId;
    private int quantity;


}
