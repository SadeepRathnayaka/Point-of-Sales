package com.point_of_sales.order_service.utils.mappers;

import com.point_of_sales.order_service.dto.request.ReqOrderDetailsDTO;
import com.point_of_sales.order_service.entity.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "inventoryId", target = "inventoryId" )
    @Mapping(source = "quantity", target = "quantity" )
    OrderDetails ReqOrderDetailsDtoToOrderDetails(ReqOrderDetailsDTO reqOrderDetailsDTO);
    List<OrderDetails> OrderDetailsDtoListToOrderDetailsList(List<ReqOrderDetailsDTO> orderDetailsDTOS);
}
