package com.point_of_sales.user_service.controller;

import com.point_of_sales.user_service.dto.request.*;
import com.point_of_sales.user_service.service.UserService;
import com.point_of_sales.user_service.utils.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/user-register")
    public ResponseEntity<StandardResponse> userRegister(@Valid @RequestBody ReqUserRegDTO regDTO, @RequestHeader("Role") String role){
        return userService.userRegister(regDTO, role);
    }

    @PostMapping(path = "/user-login")
    public ResponseEntity<StandardResponse> userLogin(@Valid @RequestBody ReqUserLogDTO userLogDTO){
        return userService.userLogin(userLogDTO);
    }

    @GetMapping(path = "/fetch-user")
    public ResponseEntity<StandardResponse> fetchUser(@RequestHeader("Username") String username){
        return userService.fetchUser(username);
    }

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<StandardResponse> forgotPassword(@RequestBody ReqForgotPwDTO reqForgotPwDTO){
        return userService.forgotPassword(reqForgotPwDTO);
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<StandardResponse> resetPassword(@RequestBody ReqResetPwDTO reqResetPwDTO){
        return userService.resetPassword(reqResetPwDTO);
    }

    @PutMapping(path = "/update-user")
    public ResponseEntity<StandardResponse> updateUser(@RequestBody ReqUpdateUserDTO reqUpdateUserDTO, @RequestHeader("Username") String username){
        return userService.updateUser(reqUpdateUserDTO, username);
    }
}
