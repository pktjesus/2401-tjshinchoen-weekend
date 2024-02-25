package com.mysite.sbb.user;

import lombok.Getter;
import lombok.val;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;
    UserRole(String value) {
        this.value = value;
    }
}
