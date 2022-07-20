package com.cdweb.backend.converters;

import com.cdweb.backend.entities.Brands;
import com.cdweb.backend.entities.Categories;
import com.cdweb.backend.payloads.requests.CategoryRequest;
import com.cdweb.backend.payloads.responses.CategoryResponse;
import com.cdweb.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryConverter {
    private final CategoryRepository categoryRepository;

    public Categories toEntity(CategoryRequest request){
        return Categories.builder()
                .name(request.getName())
//                .code(request.getCode())
                .build();
    }

    public CategoryResponse toResponse(Categories entity){
        return CategoryResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .build();
    }
}
