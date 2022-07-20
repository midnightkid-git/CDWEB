package com.cdweb.backend.services;

import com.cdweb.backend.payloads.responses.UserResponse;

import java.util.List;

public interface IUsersService {
    List<UserResponse> getAllUsers();


}
