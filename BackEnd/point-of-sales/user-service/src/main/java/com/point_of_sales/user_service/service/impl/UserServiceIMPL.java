package com.point_of_sales.user_service.service.impl;

import com.point_of_sales.user_service.dto.request.*;
import com.point_of_sales.user_service.dto.response.ResUserLogDTO;
import com.point_of_sales.user_service.dto.response.ResUserRegDTO;
import com.point_of_sales.user_service.entity.User;
import com.point_of_sales.user_service.exception.AlreadyExistsException;
import com.point_of_sales.user_service.exception.MisMatchException;
import com.point_of_sales.user_service.exception.PermissionDeniedException;
import com.point_of_sales.user_service.repo.UserRepo;
import com.point_of_sales.user_service.security.JwtService;
import com.point_of_sales.user_service.service.UserService;
import com.point_of_sales.user_service.utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public ResponseEntity<StandardResponse> userRegister(ReqUserRegDTO regDTO, String role) {

        if (!role.equals("ADMIN")){
            throw new PermissionDeniedException("Permission Denied");
        }
        if (! regDTO.getPassword().equals(regDTO.getRePassword())){
            throw new MisMatchException("Passwords Does Not Match !");
        }
        if (userRepo.existsByUserName(regDTO.getUserName()) || userRepo.existsByEmail(regDTO.getEmail()) ){
            throw new AlreadyExistsException("Username or Email Already Exists !") ;
        }

        User user = new User(
                regDTO.getUserName(),
                regDTO.getEmail(),
                regDTO.getRole(),
                passwordEncoder.encode(regDTO.getPassword())
        );

        userRepo.save(user);

        ResUserRegDTO resUserRegDTO = new ResUserRegDTO(
                user.getUserName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(
                new StandardResponse(200, "User Registered Successfully", resUserRegDTO));
    }

    @Override
    public ResponseEntity<StandardResponse> userLogin(ReqUserLogDTO userLogDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLogDTO.getUserName(),
                            userLogDTO.getPassword()));
        }catch (BadCredentialsException ex){
            throw new BadCredentialsException("Incorrect Credentials");}

        if (authentication != null && authentication.isAuthenticated()){
            User user = userRepo.findByUserName(userLogDTO.getUserName());
            String jwt = jwtService.generateToken(user.getUserName(), user.getRole());

            ResUserLogDTO resUserLogDTO = new ResUserLogDTO(
                    user.getUserName(),
                    user.getEmail(),
                    user.getRole(),
                    jwt);

            return ResponseEntity.ok(new StandardResponse(200, "User Logged In Successfully", resUserLogDTO));
        }
        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public ResponseEntity<StandardResponse> fetchUser(String username) {

        User user = userRepo.findByUserName(username);

        ResUserRegDTO resUserRegDTO = new ResUserRegDTO(
                user.getUserName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(new StandardResponse(200, "Fetch User Successfully", resUserRegDTO));
    }

    @Override
    public ResponseEntity<StandardResponse> forgotPassword(ReqForgotPwDTO reqForgotPwDTO) {

        User user = userRepo.findByEmail(reqForgotPwDTO.getEmail());
        if (user == null){throw new UsernameNotFoundException("User Not Found, Check Email Again !");}

        user.setOtp(generateOtp());
        user.setOtpExpireDate(LocalDateTime.now().plusMinutes(10));
        userRepo.save(user);
        return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", "OTP has been sent to the Email"));

    }

    @Override
    public ResponseEntity<StandardResponse> resetPassword(ReqResetPwDTO reqResetPwDTO) {

        if (!reqResetPwDTO.getPassword().equals(reqResetPwDTO.getRePassword())){
            throw new MisMatchException("Passwords Does Not Match !");
        }
        if (!validateOtp(reqResetPwDTO.getOtp())){
            throw new MisMatchException("Invalid OTP !");
        }

        User user = userRepo.findByOtp(reqResetPwDTO.getOtp());
        user.setPassword(passwordEncoder.encode(reqResetPwDTO.getPassword()));
        user.setOtp(null);
        user.setOtpExpireDate(null);
        userRepo.save(user);

        String response = "Password Updated Successfully for User : " + user.getUserName();
        return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", response));
    }

    @Override
    public ResponseEntity<StandardResponse> updateUser(ReqUpdateUserDTO reqUpdateUserDTO, String username) {

        User user = userRepo.findByUserName(username);

        if (user == null){
            throw new UsernameNotFoundException("User Not Found!");
        }

        if (!user.getUserName().equals(reqUpdateUserDTO.getUserName())){
            if (userRepo.existsByUserName(reqUpdateUserDTO.getUserName())){
                throw new AlreadyExistsException("Username is Taken");
            }
            user.setUserName(reqUpdateUserDTO.getUserName());
        }
        if (!user.getEmail().equals(reqUpdateUserDTO.getEmail())){
            if (userRepo.existsByEmail(reqUpdateUserDTO.getEmail())){
                throw new AlreadyExistsException("Email Already Exists");
            }
            user.setEmail(reqUpdateUserDTO.getEmail());
        }
        userRepo.save(user);
        return ResponseEntity.ok(new StandardResponse(200, "SUCCESS", "User Updated Successfully !"));
    }


    public String generateOtp(){
        int otp = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(otp);
    }

    public Boolean validateOtp(String otp){
        if (userRepo.existsByOtp(otp)){
            User user = userRepo.findByOtp(otp);
            return (user.getOtpExpireDate().isAfter(LocalDateTime.now()));
        }return false;
    }
}
