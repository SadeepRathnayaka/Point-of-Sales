package com.point_of_sales.order_service.service.impl;

import com.point_of_sales.customer_service.dto.response.CustomerDTO;
import com.point_of_sales.inventory_service.dto.response.ResInventoryDTO;
import com.point_of_sales.order_service.dto.request.ReqOrderDTO;
import com.point_of_sales.order_service.entity.Order;
import com.point_of_sales.order_service.entity.OrderDetails;
import com.point_of_sales.order_service.exception.NotFoundException;
import com.point_of_sales.order_service.repo.OrderDetailsRepo;
import com.point_of_sales.order_service.repo.OrderRepo;
import com.point_of_sales.order_service.service.OrderService;
import com.point_of_sales.order_service.utils.StandardResponse;
import com.point_of_sales.order_service.utils.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceIMPL implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    private final WebClient inventoryWebClient;
    private final WebClient customerWebClient;

    public OrderServiceIMPL(WebClient inventoryWebClient, WebClient customerWebClient) {
        this.inventoryWebClient = inventoryWebClient;
        this.customerWebClient = customerWebClient;
    }

    @Override
    @Transactional
    public ResponseEntity<StandardResponse> addOrder(ReqOrderDTO reqOrderDTO, boolean claimPoints) {

        String customerPhoneNumber = reqOrderDTO.getCustomerPhoneNumber();  // TODO : handle separately if the phone number if not given
        Order order = new Order();

        order.setOrderDate(reqOrderDTO.getOrderDate());
        List<OrderDetails> orderDetailsList = orderMapper.OrderDetailsDtoListToOrderDetailsList(reqOrderDTO.getOrderDetailsList());

        float totalOrderPrice = 0;
        List<OrderDetails> successInventory = new ArrayList<>();
        List<String> failedInventory  = new ArrayList<>();

        for (OrderDetails orderDetails : orderDetailsList) {

            int inventoryId = orderDetails.getInventoryId();
            int quantity = orderDetails.getQuantity();
            ResInventoryDTO resInventoryDTO = this.getInventory(inventoryId);

            if (resInventoryDTO == null) {
                throw new NotFoundException("Inventory Not Found !");
            }

            if (this.validateStock(resInventoryDTO, quantity)) {

                float unitPrice = resInventoryDTO.getInventoryPrice();
                float totalPrice = quantity * unitPrice;
                totalOrderPrice += totalPrice;

                orderDetails.setOrder(order);
                orderDetails.setInventoryId(inventoryId);
                orderDetails.setQuantity(quantity);
                orderDetails.setUnitPrice(unitPrice);
                orderDetails.setTotalPrice(totalPrice);
                successInventory.add(orderDetails);

                this.updateStock(inventoryId, quantity, -1);

            } else {
                failedInventory.add(resInventoryDTO.getName());
            }
        }

        // Customer is in the Database
        if (!customerPhoneNumber.isEmpty()){

            CustomerDTO customerDTO = this.getCustomer(customerPhoneNumber);
            if (customerDTO != null){
                order.setCustomerId(customerDTO.getCustomerId());
                if (claimPoints){
                    float claimedPoints = this.claimCustomerPoints(customerDTO.getCustomerId());
                    float discount = claimedPoints * 0.01f;
                    if (discount < totalOrderPrice) {
                        totalOrderPrice -= discount;
                    }else {
                        totalOrderPrice = 0.0f;
                    }
                }else {
                    int loyaltyPoints = (int) totalOrderPrice * 10 ;
                    this.updateCustomerPoints(customerDTO.getCustomerId(), loyaltyPoints);
                }
            }else {
                throw new NotFoundException("Customer Not Found !");
            }
        }

        // Customer not in the database
        else{
            order.setCustomerId(null);
        }

        order.setTotal(totalOrderPrice);
        orderRepo.save(order);
        orderDetailsRepo.saveAll(successInventory);

        int inventoryCount = orderDetailsList.size();
        int failedInventoryCount = failedInventory.size();
        String message;

        if (failedInventoryCount == 0){
            message = "Order Completed";
        } else if (failedInventoryCount < inventoryCount ) {
            message = "The following items are not available at the moment: " +
                    String.join(", ", failedInventory) + ".";
        }else {
            message = "Order Failed, No items are available at the moment.";
        }
        return ResponseEntity.ok(new StandardResponse(200, "OK", message));

    }


    public CustomerDTO getCustomer(String phoneNumber){

        try {
            CustomerDTO customerDTO = customerWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/customer/get-customer/{phone_number}").build(phoneNumber))
                    .retrieve()
                    .bodyToMono(CustomerDTO.class)
                    .block();

            return customerDTO;

        }catch (Exception ex){
            return null;
        }
    }

    public StandardResponse updateCustomerPoints(int customerId, int points) {
        return customerWebClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/customer/update-points")
                        .queryParam("id", customerId)
                        .queryParam("points", points)
                        .build())
                .retrieve()
                .bodyToMono(StandardResponse.class)
                .block();
    }


    public float claimCustomerPoints(int customerId) {
        Float claimedPoints = customerWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/customer/claim-points")
                        .queryParam("id", customerId)
                        .build())
                .retrieve()
                .bodyToMono(Float.class)
                .block();

        // Safe unboxing: If claimedPoints is null, return 0.0f
        return (claimedPoints != null) ? claimedPoints : 0.0f;
    }


    public ResInventoryDTO getInventory(int inventoryId){

        try {
            ResInventoryDTO resInventoryDTO = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/inventory/get-inventory/{id}").build(inventoryId))
                    .retrieve()
                    .bodyToMono(ResInventoryDTO.class)
                    .block();

            return resInventoryDTO;

        }catch (Exception ex){
            return null;
        }
    }

    public void updateStock(int inventoryId, int amount, int sign){

        StandardResponse standardResponse = inventoryWebClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/inventory/update-stock")
                        .queryParam("id", inventoryId)
                        .queryParam("amount", amount)
                        .queryParam("sign", sign)
                        .build())
                .retrieve()
                .bodyToMono(StandardResponse.class)
                .block();

    }

    public boolean validateStock(ResInventoryDTO resInventoryDTO, int quantity){
        return (resInventoryDTO.getStock() > quantity);
    }




}
