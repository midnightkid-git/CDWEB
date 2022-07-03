package com.cdweb.peakyblinders.services;


import com.cdweb.peakyblinders.common.JwtService;
import com.cdweb.peakyblinders.models.Users;
import com.cdweb.peakyblinders.payloads.AuthRequest;
import com.cdweb.peakyblinders.payloads.AuthResponse;
import com.cdweb.peakyblinders.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest request) {
        Users user = usersRepository.findByUsernameAndIsActive(request.getUsername(), true);
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("The Username or Password is Incorrect");
        }
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }
}
