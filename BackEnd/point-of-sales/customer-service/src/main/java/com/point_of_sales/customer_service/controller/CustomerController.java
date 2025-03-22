package com.point_of_sales.customer_service.controller;

import com.point_of_sales.customer_service.dto.request.ReqCustomerDTO;
import com.point_of_sales.customer_service.dto.response.CustomerDTO;
import com.point_of_sales.customer_service.service.CustomerService;
import com.point_of_sales.customer_service.utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get-customer/{phone_number}")
    public CustomerDTO getCustomer(@PathVariable(value = "phone_number") String phoneNumber){
        System.out.println("Inside get customer");
        return customerService.getCustomer(phoneNumber);
    }

    @GetMapping("/get-customer-by-id{id}")
    public CustomerDTO getCustomerById(@PathVariable(value = "id") int customerId){
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/add-customer")
    public ResponseEntity<StandardResponse> addCustomer(@RequestBody ReqCustomerDTO reqCustomerDTO){
        return customerService.addCustomer(reqCustomerDTO);
    }

    @PutMapping("/update-points")
    public ResponseEntity<StandardResponse> updatePoints(
            @RequestParam(value = "id") int customerId,
            @RequestParam(value = "points") int points
    ){
        return customerService.updatePoints(customerId, points);
    }

    @GetMapping("/claim-points")
    public float claimPoints(@RequestParam(value = "id") int customerId){
        return customerService.claimPoints(customerId);
    }
}
