package com.point_of_sales.customer_service.service.impl;

import com.point_of_sales.customer_service.dto.request.ReqCustomerDTO;
import com.point_of_sales.customer_service.dto.response.CustomerDTO;
import com.point_of_sales.customer_service.entity.Customer;
import com.point_of_sales.customer_service.exception.AlreadyExistsException;
import com.point_of_sales.customer_service.exception.NotFoundException;
import com.point_of_sales.customer_service.repo.CustomerRepo;
import com.point_of_sales.customer_service.service.CustomerService;
import com.point_of_sales.customer_service.utils.StandardResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceIMPL implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerDTO getCustomer(String phoneNumber) {

        System.out.println("Phone number :" +phoneNumber);
        if (!customerRepo.existsByPhoneNumber(phoneNumber)){
            throw new NotFoundException("Customer Not Found !");
        }

        Customer customer = customerRepo.findByPhoneNumber(phoneNumber);
        return modelMapper.map(customer, CustomerDTO.class);

    }

    @Override
    public CustomerDTO getCustomerById(int customerId) {

        if (!customerRepo.existsById(customerId)){
            throw new NotFoundException("Customer Not Found !");
        }

        Customer customer = customerRepo.getReferenceById(customerId);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public ResponseEntity<StandardResponse> addCustomer(ReqCustomerDTO reqCustomerDTO) {

        if (customerRepo.existsByPhoneNumber(reqCustomerDTO.getPhoneNumber())){
            throw new AlreadyExistsException("Phone Number Already Exists !");
        }

        Customer customer = modelMapper.map(reqCustomerDTO, Customer.class);
        customer.setLoyaltyPoints(0);
        customerRepo.save(customer);
        return ResponseEntity.ok(new StandardResponse(200,"OK","Customer Added Successfully"));
    }

    @Override
    public ResponseEntity<StandardResponse> updatePoints(int customerId, int points) {

        if (!customerRepo.existsById(customerId)){
            throw new NotFoundException("Customer Not Found !");
        }
        Customer customer = customerRepo.getReferenceById(customerId);
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);
        customerRepo.save(customer);
        return ResponseEntity.ok(new StandardResponse(200,"OK","Loyalty Points Added Successfully"));

    }

    @Override
    public float claimPoints(int customerId) {

        if (!customerRepo.existsById(customerId)){
            throw new NotFoundException("Customer Not Found !");
        }

        Customer customer = customerRepo.getReferenceById(customerId);
        int points = customer.getLoyaltyPoints();
        customer.setLoyaltyPoints(0);
        customerRepo.save(customer);
        return 0.01f * points;
    }


}
