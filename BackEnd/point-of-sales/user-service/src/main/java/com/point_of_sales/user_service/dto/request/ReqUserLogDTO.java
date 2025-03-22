package com.point_of_sales.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUserLogDTO {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
