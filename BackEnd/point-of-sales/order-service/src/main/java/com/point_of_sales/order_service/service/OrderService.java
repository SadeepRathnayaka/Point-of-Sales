package com.point_of_sales.order_service.service;

import com.point_of_sales.order_service.dto.request.ReqOrderDTO;
import com.point_of_sales.order_service.utils.StandardResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<StandardResponse> addOrder(ReqOrderDTO reqOrderDTO, boolean claimPoints);
}
