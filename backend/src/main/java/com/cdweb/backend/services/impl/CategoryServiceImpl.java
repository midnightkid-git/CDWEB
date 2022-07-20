package com.cdweb.backend.services.impl;

import com.cdweb.backend.common.Utils;
import com.cdweb.backend.converters.CategoryConverter;
import com.cdweb.backend.entities.Brands;
import com.cdweb.backend.entities.Categories;
import com.cdweb.backend.payloads.requests.CategoryRequest;
import com.cdweb.backend.payloads.responses.CategoryResponse;
import com.cdweb.backend.repositories.CategoryRepository;
import com.cdweb.backend.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public List<CategoryResponse> findAll(Pageable pageable) {
        List<CategoryResponse> responses = categoryRepository.findByIsActiveTrueOrderByModifiedDateDesc(pageable).getContent()
                .stream().map(categoryConverter :: toResponse)
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<CategoryResponse> findByIsActiveTrue() {
        return categoryRepository.findByIsActiveTrue().stream().map(categoryConverter :: toResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse save(CategoryRequest request) {
        CategoryResponse response = null;
        // id null mean insert, otherwise mean update
        if(request.getId() == null) {
            String code = request.getCode();
            Categories category = categoryRepository.findByCodeAndIsActiveTrue(code);
            if (category == null) {
                Categories newCategory = categoryConverter.toEntity(request);
                newCategory.setActive(true);
                newCategory.setCode(code);
                Categories entity = categoryRepository.save(newCategory);
                response = categoryConverter.toResponse(entity);
            } return response;
        } else {
            Categories newEntity = categoryConverter.toEntity(request);
            newEntity.setId(request.getId());
            Categories entity = categoryRepository.save(newEntity);
            return categoryConverter.toResponse(entity);
        }
    }

    @Override
    public boolean existsByCodeAndIsActiveTrue(String code) {
        return categoryRepository.existsByCodeAndIsActiveTrue(code);
    }

    @Override
    public boolean delete(Long[] ids) {
        boolean exists = true;
        for (Long id : ids) {
            if (!categoryRepository.existsByIdAndIsActiveTrue(id)) exists = false;
        }
        if (exists) {
            for (Long id :
                    ids) {
                Categories entity = categoryRepository.findByIdAndIsActiveTrue(id);
                entity.setActive(false);
                categoryRepository.save(entity);
            }
        }
        return exists;
    }

    @Override
    public int totalItem() {
        return (int) categoryRepository.countByIsActiveTrue();
    }
}
