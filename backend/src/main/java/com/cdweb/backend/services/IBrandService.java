package com.cdweb.backend.services;

import com.cdweb.backend.payloads.requests.BrandRequest;
import com.cdweb.backend.payloads.responses.BrandResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBrandService {

    List<BrandResponse> findByIsActiveTrue();

    BrandResponse save(Long id,BrandRequest request);

    int totalItem();

    List<BrandResponse> findAll();

    boolean existsByNameAndIsActiveTrue(String name);
    boolean existsByCodeAndIsActiveTrue(String code);

    boolean delete(Long [] ids);
}
