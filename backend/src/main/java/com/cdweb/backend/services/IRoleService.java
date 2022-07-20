package com.cdweb.backend.services;

import com.cdweb.backend.payloads.requests.RoleRequest;
import com.cdweb.backend.payloads.responses.RoleResponse;

public interface IRoleService {
//    List<RoleResponse> getAllRoles();
    RoleResponse create(RoleRequest request);
    RoleResponse update(RoleRequest request, Long id);
//    void delete(Long id);

}
