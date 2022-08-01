package com.cdweb.backend.controllers.user;

import com.cdweb.backend.payloads.requests.ProductFilterRequest;
import com.cdweb.backend.payloads.requests.ProductRequest;
import com.cdweb.backend.payloads.responses.PageResponse;
import com.cdweb.backend.payloads.responses.ProductResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.services.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController(value = "productControllerOfUser")
@RequestMapping("/api/v1/user/product")
@RequiredArgsConstructor
@Slf4j

public class ProductController {

    private final IProductService productService;

    @GetMapping("/no-token")
    ResponseEntity<?> getAllProduct() {
        List<ProductResponse> response = new ArrayList<>();
        response = productService.findAllForUser();
        log.info("{}", response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, response));
    }

    @GetMapping("/no-token/category/{categoryId}")
    ResponseEntity<?> getProductByFilter(@PathVariable("categoryId") Long categoryId) {
        List<ProductResponse> response = new ArrayList<>();
        response = productService.findByCategoryId(categoryId);
        log.info("{}", response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, response));
    }

    @GetMapping("/no-token/size/{size}")
    ResponseEntity<?> getProductByFilter(@PathVariable("size") String size) {
        List<ProductResponse> response = new ArrayList<>();
        response = productService.findBySize(size);
        log.info("{}", response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, response));
    }

    @GetMapping("/no-token/category/{categoryId}/size/{size}")
    ResponseEntity<?> getProductByFilter(@PathVariable("size") String size, @PathVariable("categoryId") Long categoryId) {
        List<ProductResponse> response = new ArrayList<>();
        response = productService.findBySizeAndCategory(size, categoryId);
        log.info("{}", response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, response));
    }

    @GetMapping("/no-token/{id}")
    ResponseEntity<?> getProductDetails(@PathVariable("id") Long productId) {
        ProductResponse response = productService.findByProductId(productId);
            return response != null ?  ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, response)) :
                                    ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Fail", "Can not find product!", response));
    }
}
