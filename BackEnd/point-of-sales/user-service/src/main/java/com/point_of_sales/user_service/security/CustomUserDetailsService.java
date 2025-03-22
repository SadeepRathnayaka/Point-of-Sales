package com.point_of_sales.user_service.security;

import com.point_of_sales.user_service.entity.User;
import com.point_of_sales.user_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (! userRepo.existsByUserName(username)){
            throw new UsernameNotFoundException("User Not Found !");
        }
        User user = userRepo.findByUserName(username);
        return new CustomUserDetails(user.getUserName(), user.getPassword());
    }
}
