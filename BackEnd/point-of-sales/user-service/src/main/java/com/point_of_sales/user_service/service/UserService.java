package com.point_of_sales.user_service.service;

import com.point_of_sales.user_service.dto.request.*;
import com.point_of_sales.user_service.utils.StandardResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<StandardResponse> userRegister(ReqUserRegDTO regDTO, String role);

    ResponseEntity<StandardResponse> userLogin(ReqUserLogDTO userLogDTO);

    ResponseEntity<StandardResponse> fetchUser(String username);

    ResponseEntity<StandardResponse> forgotPassword(ReqForgotPwDTO reqForgotPwDTO);

    ResponseEntity<StandardResponse> resetPassword(ReqResetPwDTO reqResetPwDTO);

    ResponseEntity<StandardResponse> updateUser(ReqUpdateUserDTO reqUpdateUserDTO, String username);
}
