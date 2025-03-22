package com.point_of_sales.user_service.dto.response;

import com.point_of_sales.user_service.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResUserRegDTO {

    private String userName;
    private String email;
    private UserRole role;


}
