package com.cdweb.peakyblinders.controllers;

import com.cdweb.peakyblinders.common.ErrorResponse;
import com.cdweb.peakyblinders.payloads.RegistrationRequest;
import com.cdweb.peakyblinders.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            return ResponseEntity.ok(registrationService.register(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ErrorResponse.builder()
                    .message(ex.getMessage())
                    .build());
        }
    }
}
