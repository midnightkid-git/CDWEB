package com.cdweb.backend.services.impl;

import com.cdweb.backend.converters.VariantConverter;
import com.cdweb.backend.entities.Attributes;
import com.cdweb.backend.entities.Variants;
import com.cdweb.backend.payloads.requests.AttributeAndVariantsRequest;
import com.cdweb.backend.payloads.requests.VariantRequest;
import com.cdweb.backend.payloads.responses.AttributeAndVariantsResponse;
import com.cdweb.backend.payloads.responses.VariantResponse;
import com.cdweb.backend.repositories.AttributeRepository;
import com.cdweb.backend.repositories.VariantRepository;
import com.cdweb.backend.services.IVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements IVariantService {
    private final VariantRepository variantRepository;

    private final VariantConverter variantConverter;

    private final AttributeRepository attributeRepository;

    @Override
    public Variants findByVariantNameAndAttributeIdAndIsActiveTrue(String variantName, Long attributeId) {
        return variantRepository.findByVariantNameAndAttributeIdAndIsActiveTrue(variantName, attributeId);
    }

    @Override
    public VariantResponse save(VariantRequest request) {
        Variants entity = variantRepository.findByIdAndIsActiveTrue(request.getId());
        if (entity == null) {
            Variants newEntity = variantConverter.toEntity(request);
            Attributes attribute = attributeRepository.findByIdAndIsActiveTrue(request.getAttributeId());
            newEntity.setAttribute(attribute);
            newEntity.setActive(true);
            Variants savedEntity = variantRepository.save(newEntity);
            VariantResponse response = variantConverter.toResponse(savedEntity);
            response.setAttributeId(savedEntity.getAttribute().getId());
            return response;
        }
        return null;
    }

    @Override
    public List<AttributeAndVariantsResponse> checkAndSaveListVariants(List<AttributeAndVariantsRequest> request) {
        List<AttributeAndVariantsResponse> response = new ArrayList<>();
        request.forEach(a -> {
            Attributes attribute = attributeRepository.findByAttributeNameAndIsActiveTrue(a.getAttributeName());
            AttributeAndVariantsResponse result =  AttributeAndVariantsResponse.builder()
                    .attributeId(attribute.getId())
                    .attributeName(attribute.getAttributeName()).build();
            List<String> variantNames = new ArrayList<>();
            for (String v : a.getVariantNames()) {
                Variants entity = variantRepository.findByVariantNameAndAttributeIdAndIsActiveTrue(v, attribute.getId());
                if (entity == null) {
                    Variants newEntity = Variants.builder()
                            .variantName(v)
                            .attribute(attribute)
                            .isActive(true)
                            .build();
                    Variants savedEntity = variantRepository.save(newEntity);
                    variantNames.add(savedEntity.getVariantName());
                } else {
                    variantNames.add(entity.getVariantName());
                }
            }
            result.setVariantNames(variantNames);
            response.add(result);
        });
        return response;
    }

    @Override
    public VariantResponse update(VariantRequest request, Long id) {
        Variants oldEntity = variantRepository.findByVariantNameAndIsActiveTrue(request.getVariantName());
        Variants entity = variantRepository.findByIdAndIsActiveTrue(id);
        if (oldEntity == null && entity != null) {
            entity.setVariantName(request.getVariantName());
            Variants updatedEntity = variantRepository.save(entity);
            VariantResponse response = variantConverter.toResponse(updatedEntity);
            response.setAttributeId(updatedEntity.getAttribute().getId());
            return response;
        }
        return null;
    }

    @Override
    public List<VariantResponse> findByAttributeIdAndIsActiveTrue(Long attributeId) {
        List<VariantResponse> response = new ArrayList<>();
        variantRepository.findByAttributeIdAndIsActiveTrue(attributeId)
                .forEach(v -> response.add(
                        VariantResponse.builder()
                                .variantName(v.getVariantName())
                                .id(v.getId())
                                .attributeId(v.getAttribute().getId())
                                .build()));
        return response;
    }

    @Override
    public List<VariantResponse> findByProductIdAndIsActive(Long productId, Long attributeId) {
        return variantRepository.findByProductIdAndIsActive(productId, attributeId)
                .stream().map(variantConverter :: toResponse)
                .collect(Collectors.toList());
    }
}
