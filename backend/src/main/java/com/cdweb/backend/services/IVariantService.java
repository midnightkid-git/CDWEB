package com.cdweb.backend.services;

import com.cdweb.backend.entities.Variants;
import com.cdweb.backend.payloads.requests.AttributeAndVariantsRequest;
import com.cdweb.backend.payloads.requests.VariantRequest;
import com.cdweb.backend.payloads.responses.AttributeAndVariantsResponse;
import com.cdweb.backend.payloads.responses.VariantResponse;

import java.util.List;

public interface IVariantService {
    Variants findByVariantNameAndAttributeIdAndIsActiveTrue(String variantName, Long attributeId);
    VariantResponse save(VariantRequest request);
    VariantResponse update(VariantRequest request, Long id);
    List<AttributeAndVariantsResponse> checkAndSaveListVariants(List<AttributeAndVariantsRequest> request);
    List<VariantResponse> findByAttributeIdAndIsActiveTrue(Long attributeId);
    List<VariantResponse> findByProductIdAndIsActive(Long productId, Long attributeId);
}
