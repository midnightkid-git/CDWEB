package com.cdweb.backend.controllers.admin;

import com.cdweb.backend.entities.Variants;
import com.cdweb.backend.payloads.requests.VariantRequest;
import com.cdweb.backend.payloads.responses.AttributeResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.payloads.responses.VariantResponse;
import com.cdweb.backend.services.IVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/variant")
@RequiredArgsConstructor

public class VariantsController {
    private final IVariantService variantService;

    @GetMapping("/{attributeId}")
    public ResponseEntity<?> getAllVariantByAttributeId(@PathVariable("attributeId") Long attributeId){
        List<VariantResponse> response = variantService.findByAttributeIdAndIsActiveTrue(attributeId);
        return ResponseEntity.status(HttpStatus.OK).body(
                (response.size()>0) ?
                        new ResponseObject("Success", null, response) :
                        new ResponseObject("Failed", "Have no variant", ""));
    }

    @PostMapping("")
    public ResponseEntity<?> insertVariants(@RequestBody VariantRequest request){
        VariantResponse response = variantService.save(request);
        return
                (response==null) ?
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("Failed", "Variant name already taken", "")) :
                        ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Success", "Insert Variant Successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVariants(@RequestBody VariantRequest request, @PathVariable("id") Long id){
        VariantResponse response = variantService.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(
                (response==null) ?
                        new ResponseObject("Failed", "Variant name already taken", "") :
                        new ResponseObject("Success", "Insert Variant Successfully", response));
    }
}
