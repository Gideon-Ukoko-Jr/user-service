package com.project.userservice.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class LoginRequest {

    @ApiModelProperty(notes = "User Email", required = true)
    @NotEmpty
    @NotNull
    private String email;

    @ApiModelProperty(notes = "User Password", required = true)
    @NotEmpty
    @NotNull
    private String password;
}
