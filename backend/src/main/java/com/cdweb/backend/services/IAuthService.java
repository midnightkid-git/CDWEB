package com.cdweb.backend.services;

import com.cdweb.backend.payloads.requests.AuthRequest;
import com.cdweb.backend.payloads.requests.ConfirmRequest;
import com.cdweb.backend.payloads.requests.RegistrationRequest;
import com.cdweb.backend.payloads.requests.UserRequest;
import com.cdweb.backend.payloads.responses.AuthResponse;
import com.cdweb.backend.payloads.responses.UserResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


public interface IAuthService {
    AuthResponse loginForUser(AuthRequest request);

    AuthResponse loginForAdmin(AuthRequest request);
    UserResponse register(RegistrationRequest request) throws MessagingException, UnsupportedEncodingException;

    AuthResponse confirmOTP(ConfirmRequest request);

    Boolean existsByUserName(String username);

    Boolean existsByGmail(String gmail);
}
