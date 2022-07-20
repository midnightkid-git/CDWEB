package com.cdweb.backend.services;

import com.cdweb.backend.entities.Attributes;
import com.cdweb.backend.payloads.requests.AttributeAndVariantsRequest;
import com.cdweb.backend.payloads.requests.AttributeRequest;
import com.cdweb.backend.payloads.responses.AttributeAndVariantsResponse;
import com.cdweb.backend.payloads.responses.AttributeResponse;
import com.cdweb.backend.payloads.responses.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAttributeService {
    AttributeAndVariantsResponse save(AttributeAndVariantsRequest request);

    List<AttributeResponse> saveListAttribute(List<String> attributeNames);

    AttributeResponse update(AttributeRequest request, Long id);

    List<AttributeResponse> findByIsActiveTrue();
    List<AttributeAndVariantsResponse> findAllAttributeAndVariants(Pageable pageable);
    List<AttributeAndVariantsResponse> findAllAttributeAndVariants();
    Attributes findByAttributeNameAndIsActiveTrue(String attributeName);

//    List<AttributeAndVariantsResponse> findAllAttributeAndVariants();

    List<AttributeResponse> findByProductIdAndIsActive(Long productId);

    int totalItem();


    boolean delete(Long[] ids);

    Boolean existsByNameAndIsActive(String attributeName);
}
