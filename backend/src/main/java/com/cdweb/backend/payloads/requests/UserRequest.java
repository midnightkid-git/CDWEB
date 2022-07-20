package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String gmail;
}
