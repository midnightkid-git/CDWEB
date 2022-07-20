package com.cdweb.backend.controllers.user;

import com.cdweb.backend.payloads.responses.BrandResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.services.IBrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value="brandsControllerOfUser")
@RequestMapping("/api/v1/user/brand")
@RequiredArgsConstructor
@Slf4j
public class BrandsController {

    private final IBrandService brandService;

    @GetMapping("/no-token")
    ResponseEntity<?> getAllIsActiveBrands(){
        List<BrandResponse> response = brandService.findByIsActiveTrue();
        return ResponseEntity.status(HttpStatus.OK).body(
                (response != null)
                        ? new ResponseObject("Success", null, response)
                        : new ResponseObject("Success", "Have no brand", null));
    }
}
