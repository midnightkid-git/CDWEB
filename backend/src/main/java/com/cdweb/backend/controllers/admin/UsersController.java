package com.cdweb.backend.controllers.admin;

import com.cdweb.backend.common.ErrorResponse;
import com.cdweb.backend.services.IUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final IUsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser() {
        try {
            return ResponseEntity.ok(usersService.getAllUsers());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ErrorResponse.builder()
                    .message(ex.getMessage())
                    .build());
        }
    }
}

