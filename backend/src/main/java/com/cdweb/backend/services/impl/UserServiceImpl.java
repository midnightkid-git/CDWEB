package com.cdweb.backend.services.impl;

import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.responses.UserResponse;
import com.cdweb.backend.repositories.UserRepository;
import com.cdweb.backend.services.IUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUsersService {
    private final UserRepository usersRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserResponse> response = new ArrayList<>();
        List<Users> entities = usersRepository.findByIsActiveTrue();
        for (Users user: entities) {
            response.add(UserResponse.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .username(user.getUsername())
                    .gmail(user.getGmail())
                    .roles(user.getRoles().getRoleName())
                    .createdBy(user.getCreatedBy())
                    .createdDate(user.getCreatedDate())
                    .modifiedBy(user.getModifiedBy())
                    .modifiedDate(user.getModifiedDate())
                    .build());
        }
        return response;
    }
}
