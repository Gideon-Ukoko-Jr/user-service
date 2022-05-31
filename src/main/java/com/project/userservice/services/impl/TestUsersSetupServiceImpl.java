package com.project.userservice.services.impl;

import com.project.userservice.entities.UserEntity;
import com.project.userservice.entities.enums.RoleEnum;
import com.project.userservice.repositories.UserRepository;
import com.project.userservice.requests.CreateUserRequest;
import com.project.userservice.services.TestUsersSetupService;
import com.project.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestUsersSetupServiceImpl implements TestUsersSetupService {
    private final UserService userService;

    @Override
    public void runSetup() {

        List<RoleEnum> admin = new ArrayList<>();
        admin.add(RoleEnum.ADMIN);
        CreateUserRequest createAdminUserRequest = CreateUserRequest.builder()
                .firstName("Test")
                .lastName("Admin")
                .email("testadmin@gmail.com")
                .password("testadmin")
                .phoneNumber("08035296428")
                .roles(admin)
                .build();

        userService.signUp(createAdminUserRequest);

        List<RoleEnum> merchant = new ArrayList<>();
        admin.add(RoleEnum.MERCHANT);
        CreateUserRequest createMerchantUserRequest = CreateUserRequest.builder()
                .firstName("Test")
                .lastName("Merchant")
                .email("testmerchant@gmail.com")
                .password("testmerchant")
                .phoneNumber("08045297429")
                .roles(merchant)
                .build();

        userService.signUp(createMerchantUserRequest);

        List<RoleEnum> client = new ArrayList<>();
        admin.add(RoleEnum.CLIENT);
        CreateUserRequest createClientUserRequest = CreateUserRequest.builder()
                .firstName("Test")
                .lastName("Client")
                .email("testclient@gmail.com")
                .password("testclient")
                .phoneNumber("08055396427")
                .roles(client)
                .build();

        userService.signUp(createClientUserRequest);

    }
}
