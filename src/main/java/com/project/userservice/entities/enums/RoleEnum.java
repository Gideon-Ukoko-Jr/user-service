package com.project.userservice.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ADMIN, MERCHANT, CLIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
