package com.cdweb.backend.services.impl;

import com.cdweb.backend.converters.ProductAttributeConverter;
import com.cdweb.backend.entities.Attributes;
import com.cdweb.backend.entities.ProductAttributes;
import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.responses.ProductAttributeResponse;
import com.cdweb.backend.repositories.ProductAttributeRepository;
import com.cdweb.backend.services.IProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements IProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;

    @Override
    public List<ProductAttributes> saveListProductAttribute(List<Attributes> attributes, Products product) {
        List<ProductAttributes> response = new ArrayList<>();
        attributes.forEach(attribute -> {
            ProductAttributes entity = productAttributeRepository.save(ProductAttributes.builder()
                    .attribute(attribute)
                    .product(product)
                    .isActive(true)
                    .build());
            response.add(entity);
        });
        return response;
    }
}
