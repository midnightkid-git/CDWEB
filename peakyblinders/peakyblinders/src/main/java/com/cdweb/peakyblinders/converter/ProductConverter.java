package com.cdweb.peakyblinders.converter;

import com.cdweb.peakyblinders.models.Product;
import com.cdweb.peakyblinders.payloads.request.ProductRequest;
import com.cdweb.peakyblinders.payloads.response.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product toEntity(ProductRequest productRequest) {
        return Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .categoryId(productRequest.getCategoryId())
                .categoryName(productRequest.getCategoryName())
//                .thumbnails(productRequest.getThumbnails())
                .build();
    }

    public ProductResponse toRes(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .categoryId(product.getCategoryId())
                .categoryName(product.getCategoryName())
//                .thumbnails(product.getThumbnails())
                .build();
    }
}
