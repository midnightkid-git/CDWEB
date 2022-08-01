package com.cdweb.backend.controllers.admin;

import com.cdweb.backend.payloads.requests.BrandRequest;
import com.cdweb.backend.payloads.responses.BrandResponse;
import com.cdweb.backend.payloads.responses.PageResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.services.IBrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController(value="brandsControllerOfAdmin")
@RequestMapping("/api/v1/admin/brand")
@RequiredArgsConstructor
@Slf4j
public class BrandsController {

    public final IBrandService brandService;

    @GetMapping("")
    ResponseEntity<?> getAll() {
        List<BrandResponse> response = brandService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                (response != null)
                        ? new ResponseObject("Success", null, response)
                        : new ResponseObject("Success", "Have no brand", null));
    }

    @PostMapping("")
    ResponseEntity<?> insertBrand(@RequestBody BrandRequest request) {
        Boolean exists = brandService.existsByNameAndIsActiveTrue(request.getName());
        if(!exists) {
            BrandResponse response = brandService.save(null,request);
            return
                    ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "Insert Brand Successfully", response));
        }else{
           return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Failed", "Brand name already taken", null));
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateBrand(@PathVariable("id") Long id,@RequestBody BrandRequest request) {
        Boolean exists = brandService.existsByNameAndIsActiveTrue(request.getName());
        if(!exists) {
            BrandResponse response = brandService.save(id,request);
            return
                    ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, response));
        }else{
            return
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Failed", "Brand name already taken", null));
        }
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteBrand(@RequestBody Long[] ids) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.delete(ids) ?
                new ResponseObject("Success", "Delete brand successfully", true) :
                new ResponseObject("Failed", "Can not find brand", false));
    }
}
