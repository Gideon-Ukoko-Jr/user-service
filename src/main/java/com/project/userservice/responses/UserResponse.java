package com.project.userservice.responses;

import com.project.userservice.entities.enums.RoleEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String uniqueCustomerId;
    private String phoneNumber;
    private List<RoleEnum> roles;
    private String token;

}
