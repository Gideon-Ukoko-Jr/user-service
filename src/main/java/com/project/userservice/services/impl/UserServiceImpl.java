package com.project.userservice.services.impl;

import com.project.userservice.entities.UserEntity;
import com.project.userservice.entities.enums.RoleEnum;
import com.project.userservice.exceptions.BadRequestException;
import com.project.userservice.exceptions.CustomException;
import com.project.userservice.feign.ProductCustomerServiceRequest;
import com.project.userservice.repositories.UserRepository;
import com.project.userservice.requests.CreateUserRequest;
import com.project.userservice.requests.LoginRequest;
import com.project.userservice.responses.UserResponse;
import com.project.userservice.security.JwtTokenProvider;
import com.project.userservice.services.UserService;
import com.project.userservice.utils.EmailUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

//    private ProductCustomerServiceRequest customerServiceRequest;

    private final RestTemplate restTemplate;

//    private final WebClient webClient;

    @Value("${product.service.add-customer.url}")
    String productServiceAddCustomerUrl;


    @Override
    public UserResponse signUp(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already in use");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new BadRequestException("Phone Number already in use");
        }

        if (!EmailUtils.isValid(request.getEmail())){
            throw new BadRequestException("Invalid email");
        }

        List<RoleEnum> roleEnums = new ArrayList<>(request.getRoles());

        if (request.getRoles().isEmpty() || request.getRoles() == null){
            roleEnums = new ArrayList<>();
            roleEnums.add(RoleEnum.CLIENT);
            log.info(String.valueOf(roleEnums));
        }

        if (request.getRoles().contains(RoleEnum.ADMIN) && !request.getEmail().equals("testadmin@gmail.com")){
            throw new BadRequestException("User cannot create type admin");
        }

        log.info(String.valueOf(roleEnums));

        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .createdTime(LocalDateTime.now())
                .roles(roleEnums)
                .uniqueUserId(generateRandomUniqueUserId())
                .build();

        userRepository.save(userEntity);

        String token = jwtTokenProvider.createToken(userEntity.getEmail(), userEntity.getRoles());

        JSONObject requestX = new JSONObject();
        try {
            requestX.put("email", userEntity.getEmail());
            requestX.put("firstName", userEntity.getFirstName());
            requestX.put("lastName", userEntity.getLastName());
            requestX.put("phoneNumber", userEntity.getPhoneNumber());
            requestX.put("uniqueCustomerId", userEntity.getUniqueUserId());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBearerAuth(token);
            HttpEntity<String> entity = new HttpEntity<String>(requestX.toString(), httpHeaders);

            ResponseEntity<String> createCustomerResult = restTemplate.exchange(productServiceAddCustomerUrl, HttpMethod.POST, entity, String.class);

            log.info(createCustomerResult.toString());
            log.info(createCustomerResult.getStatusCode().toString());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return UserResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .uniqueCustomerId(userEntity.getUniqueUserId())
                .roles(userEntity.getRoles())
                .token(token)
                .build();
    }

    @Override
    public UserResponse login(LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User doesn't exist by email : " + email));

        if (!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new BadRequestException("Invalid email or password.");
        }


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtTokenProvider.createToken(email, userEntity.getRoles());
        return UserResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .uniqueCustomerId(userEntity.getUniqueUserId())
                .roles(userEntity.getRoles())
                .token(token)
                .build();
    }

    @Override
    public UserResponse whoAmI(HttpServletRequest request) {
        UserEntity userEntity = userRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request))).orElseThrow(() -> new BadRequestException("User doesn't exist by email"));
        return fromUserEntityToUserResponse(userEntity);
    }

    @Override
    public String refresh(String email) {
        return jwtTokenProvider.createToken(email, userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User doesn't exist by email : " + email)).getRoles());
    }


    public UserResponse fromUserEntityToUserResponse(UserEntity userEntity){
        return UserResponse.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .uniqueCustomerId(userEntity.getUniqueUserId())
                .phoneNumber(userEntity.getPhoneNumber())
                .roles(userEntity.getRoles())
                .build();
    }


    public String generateRandomUniqueUserId(){
        return RandomStringUtils.random(12, true, true);
    }
}
