package com.cdweb.backend.services.impl;

import com.cdweb.backend.common.Utils;
import com.cdweb.backend.converters.ProductCombinationConverter;
import com.cdweb.backend.entities.ProductCombinations;
import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.requests.ProductCombinationRequest;
import com.cdweb.backend.payloads.responses.ProductCombinationResponse;
import com.cdweb.backend.repositories.ProductCombinationRepository;
import com.cdweb.backend.services.IProductCombinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCombinationServiceImpl implements IProductCombinationService {
    private final ProductCombinationRepository productCombinationRepository;
    private final ProductCombinationConverter productCombinationConverter;



    @Override
    public List<ProductCombinationResponse> saveListCombinations(Products product, List<ProductCombinationRequest> request) {
        List<ProductCombinationResponse> response = new ArrayList<>();
        request.forEach(r ->{
            ProductCombinations newEntity = productCombinationConverter.toEntity(r);
            newEntity.setUniqueStringId(Utils.getUniqueStringId(r.getProductVariantName()));
            newEntity.setProduct(product);
            newEntity.setActive(true);
            response.add(productCombinationConverter.toResponse(productCombinationRepository.save(newEntity)));
        });
        return response;
    }

    @Override
    public List<ProductCombinationResponse> findByProductAndIsActiveTrue(Products product) {
        return productCombinationRepository.findByProductAndIsActiveTrue(product)
                .stream().map(productCombinationConverter :: toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double minPrice(Long productId) {
        return productCombinationRepository.min(productId);
    }

    @Override
    public Double maxPrice(Long productId) {
        return productCombinationRepository.max(productId);
    }
}
