package com.cdweb.backend.controllers.admin;

import com.cdweb.backend.common.ErrorResponse;
import com.cdweb.backend.payloads.requests.RoleRequest;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.services.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/roles")
@RequiredArgsConstructor
@Slf4j
public class RolesController {
    private final IRoleService rolesService;

//    @GetMapping("")
//    public ResponseEntity<?> getAll() {
//        try {
//            return ResponseEntity.ok(rolesService.getAllRoles());
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(ErrorResponse.builder()
//                    .message(ex.getMessage())
//                    .build());
//        }
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Query Create Board Successfully", rolesService.create(request)));
        } catch (Exception ex) {
            log.error("API /api/v1/admin/roles/create: ", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot Create", ""));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody RoleRequest request, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Success", "Query Update Role Successfully", rolesService.update(request,id)));
        } catch (Exception ex) {
            log.error("API update/roles", ex);
            return ResponseEntity.badRequest().body(ErrorResponse.builder().message(ex.getMessage()).build());
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        try {
//            rolesService.delete(id);
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete Success", ""));
//        } catch (Exception ex) {
//            log.error("Delete Board", ex);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("FAILED", "Cannot Delete", ""));
//        }
//    }
}
