package com.project.userservice;

import com.project.userservice.entities.UserEntity;
import com.project.userservice.entities.enums.RoleEnum;
import com.project.userservice.requests.CreateUserRequest;
import com.project.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Autowired
    UserService userService;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(productServiceUrl)
                .build();
    }

    @Override
    public void run(String... params) throws Exception {

//        CreateUserRequest admin = CreateUserRequest.builder()
//                .firstName("Admin")
//                .lastName("User")
//                .email("adminuser@gmail.com")
//                .password("Test123$")
//                .phoneNumber("08058674523")
//                .roles(new ArrayList<RoleEnum>(Arrays.asList(RoleEnum.ADMIN)))
//                .build();
//
//        userService.signUp(admin);
//
//        CreateUserRequest client = CreateUserRequest.builder()
//                .firstName("Client")
//                .lastName("User")
//                .email("clientuser@gmail.com")
//                .password("Client123$")
//                .phoneNumber("08058673543")
//                .roles(new ArrayList<RoleEnum>(Arrays.asList(RoleEnum.CLIENT)))
//                .build();
//
//
//        userService.signUp(client);
//
//        CreateUserRequest merchant = CreateUserRequest.builder()
//                .firstName("Merchant")
//                .lastName("User")
//                .email("merchantuser@gmail.com")
//                .password("Me123$")
//                .phoneNumber("08058734563")
//                .roles(new ArrayList<RoleEnum>(Arrays.asList(RoleEnum.MERCHANT)))
//                .build();
//
//
//        userService.signUp(merchant);
    }

}
