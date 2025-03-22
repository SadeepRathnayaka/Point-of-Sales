package com.point_of_sales.user_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUpdateUserDTO {

    private String userName;
    private String email;
}
