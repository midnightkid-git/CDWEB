package com.cdweb.backend.services.impl;

import com.cdweb.backend.common.Utils;
import com.cdweb.backend.converters.ProductConverter;
import com.cdweb.backend.converters.ProductSizeConverter;
import com.cdweb.backend.entities.*;
import com.cdweb.backend.payloads.requests.AttributeAndVariantsRequest;
import com.cdweb.backend.payloads.requests.ProductFilterRequest;
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
import java.util.concurrent.atomic.AtomicBoolean;
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

    private final SizeRepository sizeRepository;

    private final IThumbnailService productGalleryService;


    @Override
    public List<ProductResponse> findAllForAdmin() {
        List<ProductResponse> response = new ArrayList<>();
        List<Products> entities = productRepository.findAll();
            entities.forEach((entity) -> {
                List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
                List<String> imageLinks = new ArrayList<>();
                List<ProductSizeRespone> productSizeRespone = new ArrayList<>();
                List<ThumbnailResponse> thumbnailResponsese = new ArrayList<>();
                entity.getSizes().forEach((size) -> {
                    productSizeRespone.add(ProductSizeRespone.builder()
                                    .quantity(size.getQuantity())
                                    .sizeId(size.getSizes().getSizeId())
                            .build());
                });
                entity.getThumbnails().forEach(img->{
                    thumbnailResponsese.add(ThumbnailResponse.builder().imageLink(img.getImageLink()).build());
                });
                productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
                ProductResponse responseProduct = ProductResponse.builder()
                        .id(entity.getId())
                        .brandName(entity.getBrands().getName())
                        .categoryName(entity.getCategories().getName())
                        .productName(entity.getProductName())
                        .description(entity.getDescription())
                        .originalPrice(String.valueOf(entity.getOriginalPrice()))
                        .originalQuantity(entity.getOriginalQuantity())
                        .discount(entity.getDiscount())
                        .isActive(entity.isActive())
                        .imageLinks(imageLinks)
                        .listSizes(productSizeRespone)
                        .build();
                response.add(responseProduct);
            });
            return response;
    }

    @Override
    public List<ProductResponse> findAllForUser() {
        List<ProductResponse> response = new ArrayList<>();
        List<Products> entities = productRepository.findAll();
        if (entities.size() > 0) {
            entities.forEach(entity -> {
                if (entity.isActive()) {
                    List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
                    List<String> imageLinks = new ArrayList<>();
                    List<ProductSizeRespone> productSizeRespone = new ArrayList<>();
                    entity.getSizes().forEach((size) -> {
                        productSizeRespone.add(ProductSizeRespone.builder()
                                .quantity(size.getQuantity())
                                .sizeId(size.getSizes().getSizeId())
                                .build());
                    });
                    productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
                    ProductResponse responseProduct = ProductResponse.builder()
                            .id(entity.getId())
                            .brandName(entity.getBrands().getName())
                            .categoryName(entity.getCategories().getName())
                            .productName(entity.getProductName())
                            .description(entity.getDescription())
                            .originalPrice(String.valueOf(entity.getOriginalPrice()))
                            .originalQuantity(entity.getOriginalQuantity())
                            .isActive(entity.isActive())
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
           List<ProductSizeRespone> productSizeRespone = new ArrayList<>();
           productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
           List<AttributeAndVariantsResponse> attrAndVarRs= new ArrayList<>();
           Categories category = categoryRepository.findByIdAndIsActiveTrue(entity.getCategories().getId());
           Brands brand = brandRepository.findByIdAndIsActiveTrue(entity.getBrands().getId());
           entity.getSizes().forEach((size) -> {
               productSizeRespone.add(ProductSizeRespone.builder()
                       .quantity(size.getQuantity())
                       .sizeId(size.getSizes().getSizeId())
                       .build());
           });
           ProductResponse product = ProductResponse.builder()
                   .id(entity.getId())
                   .productName(entity.getProductName())
                   .description(entity.getDescription())
                   .originalPrice(String.valueOf(entity.getOriginalPrice()))
                   .originalQuantity(entity.getOriginalQuantity())
                   .discount(entity.getDiscount())
                   .imageLinks(imageLinks)
                   .isActive(entity.isActive())
                   .categoryName(category.getName())
                   .brandName(brand.getName())
                   .listSizes(productSizeRespone)
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
            //lưu san phẩm
            Products savedEntity = productRepository.save(newEntity);
            request.setId(savedEntity.getId());

            productSizeConverter.toEntity(request).forEach((_x) -> {
                ProductSizes savedProductSizes = productSizeRepository.save(_x);
            });

            //Lưu hình
            List<ThumbnailResponse> thumbnailResponse = productGalleryService.save(savedEntity, request.getImageLinks());

            //lưu product combination
//            return productConverter.toResponse(savedEntity, thumbnailRespons);
            return null;
        }
        return null;
    }

    @Override
    public ProductResponse update(ProductRequest request) {
        Products entity = productRepository.findByIdAndIsActiveTrue(request.getId());
        if (entity != null) {
            Categories categories = categoryRepository.findByNameAndIsActiveTrue(request.getCategoryName());
            Brands brands = brandRepository.findByNameAndIsActiveTrue(request.getBrandName());

            entity.setProductName(request.getProductName());
            entity.setDescription(request.getDescription());
            entity.setOriginalPrice(request.getOriginalPrice());
            entity.setDiscount(request.getDiscount());
            entity.setCategories(categories);
            entity.setBrands(brands);

//            size
//            productSizeConverter.toEntity(request).forEach((_x) -> {
//                ProductSizes savedProductSizes = productSizeRepository.save(_x);
//            });
            //lưu san phẩm
            Products updatedEntity = productRepository.save(entity);

            //Lưu hình
//            List<ThumbnailResponse> thumbnailResponse = productGalleryService.save(updatedEntity, request.getImageLinks());

            //lưu product combination
//            return productConverter.toResponse(savedEntity, thumbnailRespons);
            return productConverter.toResponse(updatedEntity);
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

    @Override
    public List<ProductResponse> findByCategoryId(Long categoryId) {
        List<ProductResponse> response = new ArrayList<>();
        Categories categories = categoryRepository.findByIdAndIsActiveTrue(categoryId);
        List<Products> entities = productRepository.findByCategoriesAndIsActiveTrue(categories);
        if (entities.size() > 0) {
            entities.forEach(entity -> {
                if (entity.isActive()) {
                    List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
                    List<String> imageLinks = new ArrayList<>();
                    List<ProductSizeRespone> productSizeRespone = new ArrayList<>();
                    entity.getSizes().forEach((size) -> {
                        productSizeRespone.add(ProductSizeRespone.builder()
                                .quantity(size.getQuantity())
                                .sizeId(size.getSizes().getSizeId())
                                .build());
                    });
                    productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
                    ProductResponse responseProduct = ProductResponse.builder()
                            .id(entity.getId())
                            .brandName(entity.getBrands().getName())
                            .categoryName(entity.getCategories().getName())
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
    public List<ProductResponse> findBySize(String sizeName) {
        List<ProductResponse> response = new ArrayList<>();
        List<ProductResponse> finalResponse = new ArrayList<>();
        List<Products> entities = productRepository.findAll();
        if (entities.size() > 0) {
            entities.forEach(entity -> {
                if (entity.isActive()) {
                    AtomicBoolean isContainSize = new AtomicBoolean(false);
                    entity.getSizes().forEach((_x) -> {
                        if(_x.getSizes().getSizeId().equals(sizeName)){
                            isContainSize.set(true);
                        }
                    });
                    if(isContainSize.get()){
                    List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
                    List<String> imageLinks = new ArrayList<>();
                    List<ProductSizeRespone> productSizeRespone = new ArrayList<>();
                    entity.getSizes().forEach((size) -> {
                        productSizeRespone.add(ProductSizeRespone.builder()
                                .quantity(size.getQuantity())
                                .sizeId(size.getSizes().getSizeId())
                                .build());
                    });
                    productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
                    ProductResponse responseProduct = ProductResponse.builder()
                            .id(entity.getId())
                            .brandName(entity.getBrands().getName())
                            .categoryName(entity.getCategories().getName())
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
                }
            });
        }
        return response;
    }

    @Override
    public List<ProductResponse> findBySizeAndCategory(String sizeName, Long categoryId) {
        List<ProductResponse> response = new ArrayList<>();
        List<ProductResponse> finalResponse = new ArrayList<>();
        Categories categories = categoryRepository.findByIdAndIsActiveTrue(categoryId);
        List<Products> entities = productRepository.findByCategoriesAndIsActiveTrue(categories);
        if (entities.size() > 0) {
            entities.forEach(entity -> {
                if (entity.isActive()) {
                    AtomicBoolean isContainSize = new AtomicBoolean(false);
                    entity.getSizes().forEach((_x) -> {
                        if (_x.getSizes().getSizeId().equals(sizeName)) {
                            isContainSize.set(true);
                        }
                    });
                    if (isContainSize.get()) {
                        List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
                        List<String> imageLinks = new ArrayList<>();
                        List<ProductSizeRespone> productSizeRespone = new ArrayList<>();
                        entity.getSizes().forEach((size) -> {
                            productSizeRespone.add(ProductSizeRespone.builder()
                                    .quantity(size.getQuantity())
                                    .sizeId(size.getSizes().getSizeId())
                                    .build());
                        });
                        productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
                        ProductResponse responseProduct = ProductResponse.builder()
                                .id(entity.getId())
                                .brandName(entity.getBrands().getName())
                                .categoryName(entity.getCategories().getName())
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
                }
            });
        }
        return response;
    }

    @Override
    public boolean active(Long[] ids) {
        boolean exists = true;
        for (Long id : ids) {
            if (!productRepository.existsByIdAndIsActiveFalse(id)) exists = false;
        }
        if (exists) {
            for (Long id :
                    ids) {
                Products entity = productRepository.findByIdAndIsActiveFalse(id);
                entity.setActive(true);
                productRepository.save(entity);
            }
        }
        return exists;
    }

}
