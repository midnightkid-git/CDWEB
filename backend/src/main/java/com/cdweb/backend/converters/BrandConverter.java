package com.cdweb.backend.converters;

import com.cdweb.backend.entities.Brands;
import com.cdweb.backend.payloads.requests.BrandRequest;
import com.cdweb.backend.payloads.responses.BrandResponse;
import com.cdweb.backend.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandConverter {

    public Brands toEntity(BrandRequest request){
        return Brands.builder()
                .code(request.getCode())
                .name(request.getName())
                .build();
    }

    public BrandResponse toResponse(Brands entity){
        return BrandResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .build();
    }
}
