package com.cdweb.backend.controllers.user;

import com.cdweb.backend.payloads.responses.CategoryResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value="categoriesControllerOfUser")
@RequestMapping("/api/v1/user/category")
@RequiredArgsConstructor
@Slf4j
public class CategoriesController {

    private final ICategoryService categoryService;

    @GetMapping("/no-token")
    ResponseEntity<?> getAllIsActiveCategory(){
        List<CategoryResponse> response = categoryService.findByIsActiveTrue();
        return ResponseEntity.status(HttpStatus.OK).body(
                (response != null)
                        ? new ResponseObject("Success", null, response)
                        : new ResponseObject("Success", "Have no category", null));
    }
}
