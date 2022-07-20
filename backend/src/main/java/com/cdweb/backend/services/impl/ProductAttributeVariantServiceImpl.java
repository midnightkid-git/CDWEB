package com.cdweb.backend.services.impl;

import com.cdweb.backend.converters.ProductAttributeVariantConverter;
import com.cdweb.backend.entities.ProductAttributeVariants;
import com.cdweb.backend.entities.ProductAttributes;
import com.cdweb.backend.entities.Variants;
import com.cdweb.backend.payloads.responses.ProductAttributeVariantResponse;
import com.cdweb.backend.repositories.ProductAttributeVariantRepository;
import com.cdweb.backend.services.IProductAttributeVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeVariantServiceImpl implements IProductAttributeVariantService {
    private final ProductAttributeVariantRepository productAttributeVariantRepository;
    private final ProductAttributeVariantConverter productAttributeVariantConverter;
    @Override
    public List<ProductAttributeVariantResponse> save(List<ProductAttributes> productAttributes, List<Variants> variants) {
        List<ProductAttributeVariantResponse> response = new ArrayList<>();
        variants.forEach(variant -> {
            productAttributes.forEach(productAttribute -> {
                if (productAttribute.getAttribute().getId() == variant.getAttribute().getId()) {
                    ProductAttributeVariants entity = productAttributeVariantRepository
                            .save(ProductAttributeVariants
                                    .builder()
                                    .productAttribute(productAttribute)
                                    .variant(variant)
                                    .isActive(true)
                                    .build());
                   response.add(productAttributeVariantConverter.toResponse(productAttributeVariantRepository.save(entity)));
                }
            });
        });
        return response;
    }
}
