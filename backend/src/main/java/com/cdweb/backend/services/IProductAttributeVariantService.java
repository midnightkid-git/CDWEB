package com.cdweb.backend.services;

import com.cdweb.backend.entities.ProductAttributes;
import com.cdweb.backend.entities.Variants;
import com.cdweb.backend.payloads.responses.ProductAttributeVariantResponse;

import java.util.List;

public interface IProductAttributeVariantService {
    List<ProductAttributeVariantResponse> save(List<ProductAttributes> productAttributes, List<Variants> variants);
}
