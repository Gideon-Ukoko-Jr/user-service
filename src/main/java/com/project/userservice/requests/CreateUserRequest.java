package com.project.userservice.requests;

import com.project.userservice.entities.enums.RoleEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CreateUserRequest {

    @ApiModelProperty(notes = "Registration Email", required = true)
    @NotEmpty
    @NotNull
    private String email;


    @ApiModelProperty(notes = "Registration Password", required = true)
    @NotEmpty
    @NotNull
    private String password;

    @ApiModelProperty(notes = "User First Name", required = true)
    @NotEmpty
    @NotNull
    private String firstName;

    @ApiModelProperty(notes = "User Last Name", required = true)
    @NotEmpty
    @NotNull
    private String lastName;

    @ApiModelProperty(notes = "User Phone", required = true)
    @NotEmpty
    @NotNull
    private String phoneNumber;

    @ApiModelProperty(notes = "User Phone", required = true)
    List<RoleEnum> roles;
}
