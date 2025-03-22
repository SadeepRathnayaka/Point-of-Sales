package com.point_of_sales.user_service.dto.request;

import com.point_of_sales.user_service.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUserRegDTO {

    @NotBlank
    private String userName;

    @NotBlank
    private String email;

    private UserRole role;

    @NotBlank
    private String password;

    @NotBlank
    private String rePassword;
}
