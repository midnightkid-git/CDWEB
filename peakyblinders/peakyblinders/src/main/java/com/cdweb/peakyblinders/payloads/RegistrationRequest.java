package com.cdweb.peakyblinders.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
}
