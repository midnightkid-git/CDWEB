package com.cdweb.backend.controllers;

import com.cdweb.backend.common.Constant;
import com.cdweb.backend.common.JwtService;
import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.requests.*;
import com.cdweb.backend.payloads.responses.AuthResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.payloads.responses.UserResponse;
import com.cdweb.backend.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService authService;

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.loginForUser(request);
            Users user = jwtService.getUserFromToken(authResponse.getAccessToken());
            String refresh_token = jwtService.generateRefreshToken(user, request.getIsRememberMe());
            addRefreshTokenToCookie(response, refresh_token, jwtService.getRefreshTokenLifeTimeHours(request.getIsRememberMe()) * 60);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("success", null, authResponse));
        } catch (IllegalArgumentException ex) {
            log.error("API /login: {}", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail", ex.getMessage(), null));
        }
    }
    @PostMapping("admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.loginForAdmin(request);
            Users user = jwtService.getUserFromToken(authResponse.getAccessToken());
            String refresh_token = jwtService.generateRefreshToken(user, request.getIsRememberMe());
            addRefreshTokenToCookie(response, refresh_token, jwtService.getRefreshTokenLifeTimeHours(request.getIsRememberMe()) * 60);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("success", null, authResponse));
        } catch (IllegalArgumentException ex) {
            log.error("API /login: {}", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail", ex.getMessage(), null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            UserResponse userResponse = authService.register(request);
            Boolean exists = authService.existsByUserName(request.getUsername());
            if(exists){
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Fail", "Username is already exist", exists));
            }
            Boolean existsEmail = authService.existsByGmail(request.getGmail());
            if(existsEmail){
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Fail", "Email is already exist", existsEmail));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, userResponse));
        } catch (IllegalArgumentException ex) {
            log.error("API /register: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Fail", ex.getMessage(), null));
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Fail", ex.getMessage(), null));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Fail", ex.getMessage(), null));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
       addRefreshTokenToCookie(response, "", 0);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> confirmOTP(@RequestBody ConfirmRequest request, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.confirmOTP(request);
            Users user = jwtService.getUserFromToken(authResponse.getAccessToken());
            String refresh_token = jwtService.generateRefreshToken(user, false);

            addRefreshTokenToCookie(response, refresh_token, jwtService.getRefreshTokenLifeTimeHours(false) * 60);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, authResponse));
        } catch (IllegalArgumentException ex) {
            log.error("API /register: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Fail", ex.getMessage(), null));
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "refresh_token", required = false) String tokenCookie) {
        if (tokenCookie == null || jwtService.isNoneValidRefreshToken(tokenCookie)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseObject("Fail", "Refresh token đã hết hạn!", null));
        } else {
            Users user = jwtService.getUserFromToken(tokenCookie);
            String accessToken = jwtService.generateAccessToken(user);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Success", null, AuthResponse.builder().accessToken(accessToken).build()));
        }
    }

    public void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken, int maxAge) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

    }
}
