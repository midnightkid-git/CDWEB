package com.cdweb.peakyblinders.services;

import com.cdweb.peakyblinders.common.JwtService;
import com.cdweb.peakyblinders.models.Users;
import com.cdweb.peakyblinders.payloads.AuthResponse;
import com.cdweb.peakyblinders.payloads.RegistrationRequest;
import com.cdweb.peakyblinders.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegistrationRequest request) {
        if (usersRepository.findByUsername(request.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        Users user = usersRepository.save(Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber((request.getPhoneNumber()))
                .isActive(true)
                .build());
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }
}
