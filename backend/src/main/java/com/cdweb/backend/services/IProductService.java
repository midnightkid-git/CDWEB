package com.cdweb.backend.services;


import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.requests.ProductFilterRequest;
import com.cdweb.backend.payloads.requests.ProductRequest;
import com.cdweb.backend.payloads.responses.ProductResponse;
import com.cdweb.backend.repositories.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {

    List<ProductResponse> findAllForAdmin();

    List<ProductResponse> findAllForUser();

    int totalItem();

    ProductResponse findByProductId(Long productId);

    ProductResponse save(ProductRequest request);

    ProductResponse update(ProductRequest request);
    boolean delete(Long[] ids);

    boolean existsByProductNameAndIsActive(String productName);

    List<ProductResponse> getArrivalProducts();

    List<ProductResponse> findByCategoryId(Long categoryId);

    List<ProductResponse> findBySize(String size);

    List<ProductResponse> findBySizeAndCategory(String sizeName, Long categoryId);

    boolean active(Long[] ids);
}
