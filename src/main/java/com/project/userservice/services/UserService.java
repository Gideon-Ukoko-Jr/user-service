package com.project.userservice.services;

import com.project.userservice.entities.UserEntity;
import com.project.userservice.entities.enums.RoleEnum;
import com.project.userservice.requests.CreateUserRequest;
import com.project.userservice.requests.LoginRequest;
import com.project.userservice.responses.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {

    UserResponse signUp(CreateUserRequest request);

    UserResponse login(LoginRequest request);

    UserResponse whoAmI(HttpServletRequest request);

    String refresh(String email);
}
