package com.cdweb.backend.controllers.admin;

import com.cdweb.backend.converters.ProductConverter;
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

import java.util.List;

@RestController(value="productControllerOfAdmin")
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final IProductService productService;

    private final ProductConverter productConverter;


    // this request is: http://localhost:8081/api/v1/products?page=1&limit=3
    @GetMapping("getListProduct")
    ResponseEntity<?> showAllProduct() {
        List<ProductResponse> response = productService.findAllForAdmin();
        return response.size()>0 ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Success", null, response)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("Failed", "Have no product", null)) ;
    }

    @PostMapping("")    
    ResponseEntity<?> postProduct(@RequestBody ProductRequest request) {
        Boolean exists = productService.existsByProductNameAndIsActive(request.getProductName());
        if(exists){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Fail", "Product named '" + request.getProductName() + "' already exists", exists));
        }else{
        ProductResponse response = productService.save(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Success", "Insert Product Successfully", response));
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateProduct(@RequestBody ProductRequest request, @PathVariable("id") Long id) {
        request.setId(id);
        ProductResponse updatedProduct = productService.update(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update Product Successfully", updatedProduct));
    }

    //delete
    @DeleteMapping("/{ids}")
    ResponseEntity<?> deleteProduct(@PathVariable("ids") Long[] ids) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.delete(ids) ?
                new ResponseObject("Success", "Delete Product successfully", true) :
                new ResponseObject("Failed", "Can not find product", false));
    }

    @PostMapping("/active/{ids}")
    ResponseEntity<?> activeProduct(@PathVariable("ids") Long[] ids) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.active(ids) ?
                new ResponseObject("Success", "Active Product successfully", true) :
                new ResponseObject("Failed", "Can not find product", false));
    }


}
