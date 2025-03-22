package com.point_of_sales.user_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqResetPwDTO {

    private String otp;
    private String password;
    private String rePassword;
}
