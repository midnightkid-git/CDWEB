package com.cdweb.backend.controllers.user;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "productControllerOfUser")
@RequestMapping("/api/v1/user/product")
@RequiredArgsConstructor
@Slf4j

public class ProductController {

    private final IProductService productService;

    @GetMapping("/no-token/{page}/{limit}")
    ResponseEntity<?> getAllProduct(@PathVariable("page") int page, @PathVariable("limit") int limit) {
        PageResponse<ProductResponse> response = new PageResponse<>();
        response.setPage(page);
        Pageable pageable = PageRequest.of(page - 1, limit);
        response.setTotalPages((int) Math.ceil((double) (productService.totalItem()) / limit));
        response.setData(productService.findAllForUser(pageable));
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
