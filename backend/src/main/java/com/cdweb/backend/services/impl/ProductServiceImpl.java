package com.cdweb.backend.services.impl;

import com.cdweb.backend.common.Utils;
import com.cdweb.backend.converters.ProductConverter;
import com.cdweb.backend.converters.ProductSizeConverter;
import com.cdweb.backend.entities.*;
import com.cdweb.backend.payloads.requests.AttributeAndVariantsRequest;
import com.cdweb.backend.payloads.requests.ProductRequest;
import com.cdweb.backend.payloads.responses.*;
import com.cdweb.backend.repositories.*;
import com.cdweb.backend.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    private final ProductConverter productConverter;
    private final ProductSizeConverter productSizeConverter;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    private  final ProductSizeRepository productSizeRepository;

    private final IThumbnailService productGalleryService;


    @Override
    public List<ProductResponse> findAllForAdmin(Pageable pageable) {
        List<ProductResponse> response = new ArrayList<>();
        List<Products> entities = productRepository.findByIsActiveTrueOrderByModifiedDateDesc(pageable).getContent();
        if(entities.size() > 0){
            entities.forEach((entity) -> {
                List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
                List<String> imageLinks = new ArrayList<>();
                List<ProductSizeRespone> productSizeRespone = new ArrayList<>();
                entity.getSizes().forEach((size) -> {
                    productSizeRespone.add(ProductSizeRespone.builder()
                                    .quantity(size.getQuantity())
                                    .sizeName(size.getSizes().getSizeName())
                            .build());
                });
                productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
                ProductResponse responseProduct = ProductResponse.builder()
                        .id(entity.getId())
                        .productName(entity.getProductName())
                        .description(entity.getDescription())
                        .originalPrice(String.valueOf(entity.getOriginalPrice()))
                        .originalQuantity(entity.getOriginalQuantity())
                        .discount(entity.getDiscount())
                        .imageLinks(imageLinks)
                        .listSizes(productSizeRespone)
                        .build();
                response.add(responseProduct);
            });
            return response;
        }
        return null;
    }

    @Override
    public List<ProductResponse> findAllForUser(Pageable pageable) {
        List<ProductResponse> response = new ArrayList<>();
        List<Products> entities = productRepository.findAll(pageable).getContent();
        if (entities.size() > 0) {
            entities.forEach(entity -> {
                if (entity.isActive()) {
                    List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
                    List<String> imageLinks = new ArrayList<>();
                    List<ProductSizeRespone> productSizeRespone = new ArrayList<>();
                    entity.getSizes().forEach((size) -> {
                        productSizeRespone.add(ProductSizeRespone.builder()
                                .quantity(size.getQuantity())
                                .sizeName(size.getSizes().getSizeName())
                                .build());
                    });
                    productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
                    ProductResponse responseProduct = ProductResponse.builder()
                            .id(entity.getId())
                            .productName(entity.getProductName())
                            .description(entity.getDescription())
                            .originalPrice(String.valueOf(entity.getOriginalPrice()))
                            .originalQuantity(entity.getOriginalQuantity())
                            .discount(entity.getDiscount())
                            .imageLinks(imageLinks)
                            .listSizes(productSizeRespone)
                            .build();
                    response.add(responseProduct);
                }
            });
        }
        return response;
    }

    @Override
    public int totalItem() {
        return (int) productRepository.countByIsActiveTrue();
    }


    @Override
    public ProductResponse findByProductId(Long productId) {
        Products entity = productRepository.findByIdAndIsActiveTrue(productId);
       if (entity != null) {
           List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
           List<String> imageLinks = new ArrayList<>();
           productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
           List<AttributeAndVariantsResponse> attrAndVarRs= new ArrayList<>();
           Categories category = categoryRepository.findByIdAndIsActiveTrue(entity.getCategories().getId());
           Brands brand = brandRepository.findByIdAndIsActiveTrue(entity.getBrands().getId());
           ProductResponse product = ProductResponse.builder()
                   .id(entity.getId())
                   .productName(entity.getProductName())
                   .description(entity.getDescription())
                   .originalPrice(String.valueOf(entity.getOriginalPrice()))
                   .originalQuantity(entity.getOriginalQuantity())
                   .discount(entity.getDiscount())
                   .imageLinks(imageLinks)
                   .categoryName(category.getName())
                   .brandName(brand.getName())
                   .build();
           return product;
       }
       return null;
    }

    @Override
    public ProductResponse save(ProductRequest request) {
        Products entity = productRepository.findByProductNameAndIsActiveTrue(request.getProductName());
        if (entity == null) {
            List<ProductSizes> listSizesEntity = new ArrayList<>();
            Products newEntity = productConverter.toEntity(request);
            Categories category = categoryRepository.findByNameAndIsActiveTrue(request.getCategoryName());
            Brands brand = brandRepository.findByNameAndIsActiveTrue(request.getBrandName());
            newEntity.setCategories(category);
            newEntity.setBrands(brand);
            newEntity.setActive(true);

            productSizeConverter.toEntity(request).forEach((_x) -> {
                ProductSizes savedProductSizes = productSizeRepository.save(_x);
            });
            //lưu san phẩm
            Products savedEntity = productRepository.save(newEntity);

            //Lưu hình
            List<ThumbnailResponse> thumbnailResponse = productGalleryService.save(savedEntity, request.getImageLinks());

            //lưu product combination
//            return productConverter.toResponse(savedEntity, thumbnailRespons);
            return null;
        }
        return null;
    }

    @Override
    public boolean delete(Long[] ids) {
        boolean exists = true;
        for (Long id : ids) {
            if (!productRepository.existsByIdAndIsActiveTrue(id)) exists = false;
        }
        if (exists) {
            for (Long id :
                    ids) {
                Products entity = productRepository.findByIdAndIsActiveTrue(id);
                entity.setActive(false);
                productRepository.save(entity);
            }
        }
        return exists;
    }

    @Override
    public boolean existsByProductNameAndIsActive(String productName) {
        return productRepository.existsByProductNameAndIsActiveTrue(productName);
    }

    @Override
    public List<ProductResponse> getArrivalProducts() {
        return null;
    }


}
