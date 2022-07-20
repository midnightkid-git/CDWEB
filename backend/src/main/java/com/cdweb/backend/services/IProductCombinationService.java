package com.cdweb.backend.services;

import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.requests.ProductCombinationRequest;
import com.cdweb.backend.payloads.responses.ProductCombinationResponse;

import java.util.List;

public interface IProductCombinationService {

    List<ProductCombinationResponse> saveListCombinations(Products product, List<ProductCombinationRequest> request);

    List<ProductCombinationResponse> findByProductAndIsActiveTrue(Products product);

    Double minPrice(Long productId);

    Double maxPrice(Long productId);
}
