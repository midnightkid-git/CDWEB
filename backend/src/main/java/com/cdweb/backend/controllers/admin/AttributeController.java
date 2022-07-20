package com.cdweb.backend.controllers.admin;

import com.cdweb.backend.payloads.requests.AttributeAndVariantsRequest;
import com.cdweb.backend.payloads.requests.AttributeRequest;
import com.cdweb.backend.payloads.responses.*;
import com.cdweb.backend.services.IAttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value ="attributeControllerOfAdmin")
@RequestMapping("/api/v1/admin/attribute")
@RequiredArgsConstructor
@Slf4j
public class AttributeController {
    private final IAttributeService attributeService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        List<AttributeResponse> response = attributeService.findByIsActiveTrue();
        return ResponseEntity.status(HttpStatus.OK).body(
                (response.size()>0) ?
                        new ResponseObject("Success", null, response) :
                        new ResponseObject("Failed", "Have no attribute", null));
    }

    @GetMapping("/{page}/{limit}")
    public ResponseEntity<?> getAllAttrAndVariants(@PathVariable("page") int page, @PathVariable("limit") int limit){
        PageResponse<AttributeAndVariantsResponse> response = new PageResponse<>();
        response.setPage(page);
        Pageable pageable = PageRequest.of(page - 1, limit);
        int totalItem = attributeService.totalItem();
        response.setTotalItems(totalItem);
        response.setTotalPages((int) Math.ceil((double) (totalItem) / limit));
        response.setData(attributeService.findAllAttributeAndVariants(pageable));
        return response.getData().size()>0 ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Success", null, response)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("Failed", "Have no attribute", null)) ;
    }

    @GetMapping("/listVariants")
    public ResponseEntity<?> getAllAttrAndVariants(){
        List<AttributeAndVariantsResponse> response = attributeService.findAllAttributeAndVariants();
        return ResponseEntity.status(HttpStatus.OK).body(
                (response.size()>0) ?
                        new ResponseObject("Success", null, response) :
                        new ResponseObject("Failed", "Have no attribute", null));
    }

    @PostMapping("")
    public ResponseEntity<?> insertAttributeAndVariants(@RequestBody AttributeAndVariantsRequest request){
        AttributeAndVariantsResponse response = attributeService.save(request);
        return response==null ?
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("Failed", "Attribute name already taken", null)) :
                        ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Success", "Insert Attribute Successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttribute(@RequestBody AttributeRequest request, @PathVariable("id") Long id){
        AttributeResponse response = attributeService.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(
                (response==null) ?
                        new ResponseObject("Failed", "Attribute name already taken", "") :
                        new ResponseObject("Success", "Insert Attribute Successfully", response));
    }

    @DeleteMapping("")
    ResponseEntity<?> deleteAttribute(@RequestBody Long[] ids) {
        return ResponseEntity.status(HttpStatus.OK).body(attributeService.delete(ids) ?
                new ResponseObject("Success", "Delete attribute successfully", true) :
                new ResponseObject("Failed", "Can not find brand", false));
    }

    @GetMapping("/exists/{attributeName}")
    ResponseEntity<?> existsAttributeByName(@PathVariable("attributeName") String attributeName) {
        Boolean exists = attributeService.existsByNameAndIsActive(attributeName);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", "", exists));
    }
}
