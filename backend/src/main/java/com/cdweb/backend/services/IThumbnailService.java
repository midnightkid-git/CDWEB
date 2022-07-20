package com.cdweb.backend.services;

import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.responses.ThumbnailResponse;

import java.util.List;

public interface IThumbnailService {
    List<ThumbnailResponse> save(Products product, List<String> imageLinks);
    List<ThumbnailResponse>findByProductAndIsActiveTrue(Products productId);
}
