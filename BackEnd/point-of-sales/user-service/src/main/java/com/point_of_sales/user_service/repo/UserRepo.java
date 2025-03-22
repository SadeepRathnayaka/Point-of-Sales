package com.point_of_sales.user_service.repo;

import com.point_of_sales.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    User findByUserName(String username);

    User findByEmail(String email);

    boolean existsByOtp(String otp);

    User findByOtp(String otp);
}
