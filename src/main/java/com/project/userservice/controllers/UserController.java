package com.project.userservice.controllers;

import com.project.userservice.requests.CreateUserRequest;
import com.project.userservice.requests.LoginRequest;
import com.project.userservice.responses.UserResponse;
import com.project.userservice.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@Api(tags = "User Endpoints", description = "Handles Authentication")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/sign-in")
    @ApiOperation(value = "${UserController.sign-in}")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request){
        UserResponse response = userService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @ApiOperation(value = "${UserController.sign-up}")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest request){
        UserResponse response = userService.signUp(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_MERCHANT')")
    public UserResponse whoAmI(HttpServletRequest request){
        return userService.whoAmI(request);
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT') or hasRole('ROLE_MERCHANT')")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }
}
