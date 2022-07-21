package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;
    private Boolean isRememberMe;
}
