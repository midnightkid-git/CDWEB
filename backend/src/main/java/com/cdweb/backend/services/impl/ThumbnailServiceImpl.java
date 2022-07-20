package com.cdweb.backend.services.impl;

import com.cdweb.backend.converters.ThumbnailConverter;
import com.cdweb.backend.entities.Thumbnails;
import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.responses.ThumbnailResponse;
import com.cdweb.backend.repositories.ThumbnailRepository;
import com.cdweb.backend.services.IThumbnailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ThumbnailServiceImpl implements IThumbnailService {
    private final ThumbnailRepository thumbnailRepository;
    private final ThumbnailConverter thumbnailConverter;

    @Override
    public List<ThumbnailResponse> save(Products product, List<String> imageLinks) {
        List<ThumbnailResponse> response = new ArrayList<>();
        imageLinks.forEach(e -> response
                .add(thumbnailConverter.toResponse(
                        thumbnailRepository.save(
                                Thumbnails.builder()
                                        .imageLink(e)
                                        .product(product)
                                        .isActive(true)
                                        .build()))));
        return response;
    }

    @Override
    public List<ThumbnailResponse> findByProductAndIsActiveTrue(Products productId) {
        return thumbnailRepository.findByProductAndIsActiveTrue(productId)
                .stream().map(thumbnailConverter::toResponse)
                .collect(Collectors.toList());
    }
}
