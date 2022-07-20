package com.cdweb.backend.services;


import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.requests.ProductRequest;
import com.cdweb.backend.payloads.responses.ProductResponse;
import com.cdweb.backend.repositories.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {

    List<ProductResponse> findAllForAdmin(Pageable pageable);

    List<ProductResponse> findAllForUser(Pageable pageable);

    int totalItem();

    ProductResponse findByProductId(Long productId);

    ProductResponse save(ProductRequest request);

    boolean delete(Long[] ids);

    boolean existsByProductNameAndIsActive(String productName);

    List<ProductResponse> getArrivalProducts();


}
