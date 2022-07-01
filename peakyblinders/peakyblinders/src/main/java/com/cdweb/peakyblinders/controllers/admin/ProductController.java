package com.cdweb.peakyblinders.controllers.admin;
import com.cdweb.peakyblinders.converter.ProductConverter;
import com.cdweb.peakyblinders.converter.SizeConverter;
import com.cdweb.peakyblinders.models.Product;
import com.cdweb.peakyblinders.request.ProductRequest;
import com.cdweb.peakyblinders.response.ProductResponse;
import com.cdweb.peakyblinders.response.ResponseObject;
import com.cdweb.peakyblinders.response.SizeResponse;
import com.cdweb.peakyblinders.services.IProductService;
import com.cdweb.peakyblinders.services.ProductService;
import com.cdweb.peakyblinders.services.SizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController(value = "ProductControllerForAdmin")
@RequestMapping(path = "/api/v1/Products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final SizeService sizeService;
    private final SizeConverter sizeConverter;
    private final IProductService productService;
    private final ProductConverter productConverter;
    @GetMapping("")
    // this request is : http://localhost:8081/api/v1/Products
    List<ProductResponse> getAllProducts(){
    return    productService.getAllProducts();
    }

    @PostMapping("/insert")
    ResponseEntity<?> insertProduct (@RequestBody ProductRequest request){
        Product p = productService.saveSize(request);
        List<SizeResponse> sizeResponses = new ArrayList<>();

        request.getSizes().forEach(s->{
            sizeResponses.add(sizeService.saveSize(sizeConverter.toEntity(s),p));
        });
        ProductResponse productResponse = productConverter.toRes(p);
        productResponse.setSize(sizeResponses);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","insert successfully",productResponse)
        );
    }
}
