package com.cdweb.backend.controllers.admin;

import com.cdweb.backend.payloads.requests.CategoryRequest;
import com.cdweb.backend.payloads.responses.CategoryResponse;
import com.cdweb.backend.payloads.responses.CategoryResponse;
import com.cdweb.backend.payloads.responses.PageResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.services.ICategoryService;
import com.cdweb.backend.services.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value="categoriesControllerOfAdmin")
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
@Slf4j
public class CategoriesController {

    private final ICategoryService categoryService;

    @GetMapping("/page/{page}/limit/{limit}")
    ResponseEntity<?> getAll(@PathVariable("page") int page, @PathVariable("limit") int limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        int totalItem = categoryService.totalItem();
        PageResponse<CategoryResponse> response = PageResponse.<CategoryResponse>builder()
                .page(page)
                .totalPages((int) Math.ceil((double) (totalItem) / limit))
                .totalItems(totalItem)
                .data(categoryService.findAll(pageable))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(
                (response.getData().size() > 0)
                        ? new ResponseObject("Success", null, response)
                        : new ResponseObject("Success", "Have no category", null));
    }

    @GetMapping("")
    ResponseEntity<?> getAllIsActiveCategory(){
        List<CategoryResponse> response = categoryService.findByIsActiveTrue();
        return ResponseEntity.status(HttpStatus.OK).body(
                (response != null)
                        ? new ResponseObject("Success", null, response)
                        : new ResponseObject("Success", "Have no category", null));
    }

    @PostMapping("")
    ResponseEntity<?> insertCategory(@RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.save(request);
        return
                ResponseEntity.status(HttpStatus.OK).body((response != null)
                        ? new ResponseObject("Success", "Insert Category Successfully", response)
                        : new ResponseObject("Failed", "Category name already taken", null));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateCategory(@PathVariable("id") Long id,@RequestBody CategoryRequest request) {
        request.setId(id);
        CategoryResponse response = categoryService.save(request);
        return
                ResponseEntity.status(HttpStatus.OK).body((response != null)
                        ? new ResponseObject("Success", null, response)
                        : new ResponseObject("Failed", "Have no category", null));
    }

    @GetMapping("/exists/{code}")
    ResponseEntity<?> existsCategoryByCode(@PathVariable("code") String code) {
        log.info("pn {}", code);
        Boolean exists = categoryService.existsByCodeAndIsActiveTrue(code);
        log.info("kq {}", exists);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "", exists));
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteCategory(@RequestBody Long[] ids) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.delete(ids) ?
                new ResponseObject("Success", "Delete category successfully", true) :
                new ResponseObject("Failed", "Can not find category", false));
    }
}

