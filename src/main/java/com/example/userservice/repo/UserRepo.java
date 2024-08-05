package com.example.userservice.repo;

import com.example.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository <User,Long >{
	User findByUsername (String username );
	User findByEmail(String email);
	@Query (value = "SELECT id FROM User  WHERE reset_password_token = ?1",nativeQuery = true)
	Long findByResetPasswordToken(String token); }


