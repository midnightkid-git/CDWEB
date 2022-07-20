package com.cdweb.backend.services;

import com.cdweb.backend.payloads.requests.CategoryRequest;
import com.cdweb.backend.payloads.responses.CategoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponse> findAll(Pageable pageable);

    List<CategoryResponse> findByIsActiveTrue();

    CategoryResponse save(CategoryRequest request);

    int totalItem();
    boolean existsByCodeAndIsActiveTrue(String code);

    boolean delete(Long [] ids);
}
