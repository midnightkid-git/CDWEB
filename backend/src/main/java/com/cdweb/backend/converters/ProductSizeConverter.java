package com.cdweb.backend.converters;

import com.cdweb.backend.entities.ProductSizeKey;
import com.cdweb.backend.entities.ProductSizes;
import com.cdweb.backend.entities.Products;
import com.cdweb.backend.entities.Sizes;
import com.cdweb.backend.payloads.requests.ProductRequest;
import com.cdweb.backend.payloads.responses.AttributeAndVariantsResponse;
import com.cdweb.backend.payloads.responses.ProductCombinationResponse;
import com.cdweb.backend.payloads.responses.ProductResponse;
import com.cdweb.backend.payloads.responses.ThumbnailResponse;
import com.cdweb.backend.repositories.ProductRepository;
import com.cdweb.backend.repositories.ProductSizeRepository;
import com.cdweb.backend.repositories.SizeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductSizeConverter {
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    public List<ProductSizes> toEntity(ProductRequest request) {
        List<ProductSizes> listEntity = new ArrayList<>();
        request.getSizes().forEach((_x) -> {
            Products products = productRepository.findById(request.getId()).get();
            Sizes sizes = sizeRepository.findBySizeId(_x.getSize_id());
//            ProductSizeKey productSizeKey = ProductSizeKey.builder()
//                    .productId(request.getId())
//                    .sizeId(_x.getSize_id())
//                    .build();
            listEntity.add(ProductSizes.builder()
                            .quantity(_x.getQuantity())
                            .product(products)
                            .sizes(sizes)
                    .build());

        });

        return listEntity;
    }
    public ProductResponse toResponse(Products entity, List<ThumbnailResponse> productGalleries,
                                      List<AttributeAndVariantsResponse> attrAndVarRs,
                                      List<ProductCombinationResponse> proComRs) {
        List<String> imageLinks = new ArrayList<>();
        productGalleries.forEach(p -> imageLinks.add(p.getImageLink()));
        return ProductResponse.builder()
                .id(entity.getId())
                .productName(entity.getProductName())
                .description(entity.getDescription())
                .originalPrice(String.valueOf(entity.getOriginalPrice()))
                .originalQuantity(entity.getOriginalQuantity())
                .discount(entity.getDiscount())
                .imageLinks(imageLinks)
                .categoryName(entity.getCategories().getName())
                .brandName(entity.getBrands().getName())
                .createdDate(entity.getCreatedDate())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }
}
