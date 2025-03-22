package com.point_of_sales.order_service.controller;

import com.point_of_sales.order_service.dto.request.ReqOrderDTO;
import com.point_of_sales.order_service.service.OrderService;
import com.point_of_sales.order_service.utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/add-order")
    public ResponseEntity<StandardResponse> addOrder(
            @RequestBody ReqOrderDTO reqOrderDTO,
            @RequestParam(value = "claim_points") boolean claimPoints
    ){
        return orderService.addOrder(reqOrderDTO, claimPoints);
    }
}
