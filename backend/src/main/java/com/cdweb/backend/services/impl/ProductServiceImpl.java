package com.cdweb.backend.services.impl;

import com.cdweb.backend.common.Utils;
import com.cdweb.backend.converters.ProductAttributeConverter;
import com.cdweb.backend.converters.ProductConverter;
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

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    private final IAttributeService attributeService;

    private final IVariantService variantService;

    private final IProductAttributeService productAttributeService;

    private final IThumbnailService productGalleryService;

    private final IProductAttributeVariantService productAttributeVariantService;

    private final IProductCombinationService productCombinationService;


    @Override
    public List<ProductResponse> findAllForAdmin(Pageable pageable) {
        List<ProductResponse> response = new ArrayList<>();
        List<Products> entities = productRepository.findByIsActiveTrueOrderByModifiedDateDesc(pageable).getContent();
       if (entities.size() > 0) {
           entities.forEach(entity -> {
               if (entity.isActive()) {
                   List<ThumbnailResponse> productThumbnails = productGalleryService.findByProductAndIsActiveTrue(entity);
                   List<AttributeResponse> attributes = attributeService.findByProductIdAndIsActive(entity.getId());
                   List<AttributeAndVariantsResponse> attrAndVarRs= new ArrayList<>();
                   attributes.forEach(a -> {
                       List<String> variants = variantService.findByProductIdAndIsActive(entity.getId(), a.getId())
                               .stream().map(VariantResponse :: getVariantName).collect(Collectors.toList());
                       AttributeAndVariantsResponse attrAndVar = AttributeAndVariantsResponse.builder()
                               .attributeId(a.getId())
                               .attributeName(a.getAttributeName())
                               .variantNames(variants)
                               .build();
                       attrAndVarRs.add(attrAndVar);
                   });
                   List<ProductCombinationResponse> proComRs = productCombinationService.findByProductAndIsActiveTrue(entity);
                   ProductResponse product = productConverter.toResponse(entity, productThumbnails, attrAndVarRs, proComRs);
                   response.add(product);
               }
           });
       }
        return response;
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
                    productThumbnails.forEach(p -> imageLinks.add(p.getImageLink()));
                    ProductResponse product = ProductResponse.builder()
                            .id(entity.getId())
                            .productName(entity.getProductName())
                            .description(entity.getDescription())
                            .originalPrice(String.valueOf(entity.getOriginalPrice()))
                            .originalQuantity(entity.getOriginalQuantity())
                            .discount(entity.getDiscount())
                            .imageLinks(imageLinks)
                            .build();
                    List<ProductCombinationResponse> proComRs = productCombinationService.findByProductAndIsActiveTrue(entity);
                    if (proComRs != null) {
                        int quantity = 0;
                       for(ProductCombinationResponse p : proComRs) {
                           quantity += p.getQuantity();
                       }
                       product.setOriginalQuantity(quantity);
                    }
                    Double maxPrice = productCombinationService.maxPrice(entity.getId());
                    Double minPrice = productCombinationService.minPrice(entity.getId());
                    if (maxPrice!= null && minPrice != null){
                        if(maxPrice.equals(minPrice)){
                            product.setOriginalPrice(Utils.formatNumber(maxPrice));
                        } else {
                            product.setOriginalPrice(Utils.formatNumber(minPrice) + " - " + Utils.formatNumber(maxPrice));
                        }
                    }
                    response.add(product);
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
           List<AttributeResponse> attributes = attributeService.findByProductIdAndIsActive(entity.getId());
           List<AttributeAndVariantsResponse> attrAndVarRs= new ArrayList<>();
           attributes.forEach(a -> {
               List<String> variants = variantService.findByProductIdAndIsActive(entity.getId(), a.getId())
                       .stream().map(VariantResponse :: getVariantName).collect(Collectors.toList());
               AttributeAndVariantsResponse attrAndVar = AttributeAndVariantsResponse.builder()
                       .attributeId(a.getId())
                       .attributeName(a.getAttributeName())
                       .variantNames(variants)
                       .build();
               attrAndVarRs.add(attrAndVar);
           });
           Categories category = categoryRepository.findByIdAndIsActiveTrue(entity.getCategories().getId());
           Brands brand = brandRepository.findByIdAndIsActiveTrue(entity.getBrands().getId());
           ProductResponse product = ProductResponse.builder()
                   .id(entity.getId())
                   .productName(entity.getProductName())
                   .description(entity.getDescription())
                   .originalPrice(String.valueOf(entity.getOriginalPrice()))
                   .originalQuantity(entity.getOriginalQuantity())
                   .discount(entity.getDiscount())
                   .attributeAndVariants(attrAndVarRs)
                   .imageLinks(imageLinks)
                   .categoryName(category.getName())
                   .brandName(brand.getName())
                   .build();
           List<ProductCombinationResponse> proComRs = productCombinationService.findByProductAndIsActiveTrue(entity);
           if (proComRs != null) {
               int quantity = 0;
               for(ProductCombinationResponse p : proComRs) {
                   quantity += p.getQuantity();
               }
               product.setOriginalQuantity(quantity);
           }
           Double maxPrice = productCombinationService.maxPrice(entity.getId());
           Double minPrice = productCombinationService.minPrice(entity.getId());
           if (maxPrice!= null && minPrice != null){
               if(maxPrice.equals(minPrice)){
                   product.setOriginalPrice(Utils.formatNumber(maxPrice));
               } else {
                   product.setOriginalPrice(Utils.formatNumber(minPrice) + " - " + Utils.formatNumber(maxPrice));
               }
           }
           return product;
       }
       return null;
    }

    @Override
    public ProductResponse save(ProductRequest request) {
        Products entity = productRepository.findByProductNameAndIsActiveTrue(request.getProductName());
        if (entity == null) {
            Products newEntity = productConverter.toEntity(request);
            Categories category = categoryRepository.findByNameAndIsActiveTrue(request.getCategoryName());
            Brands brand = brandRepository.findByNameAndIsActiveTrue(request.getBrandName());
            newEntity.setCategories(category);
            newEntity.setBrands(brand);
            newEntity.setActive(true);

            //lưu san phẩm
            Products savedEntity = productRepository.save(newEntity);
            //Lưu hình
            List<ThumbnailResponse> thumbnailRespons = productGalleryService.save(savedEntity, request.getImageLinks());

            List<String> listOfAttributeNames = new ArrayList<>();
            List<AttributeAndVariantsRequest> attributesAndVariantsRequest = new ArrayList<>();
            request.getAttributes().forEach(a -> {
                listOfAttributeNames.add(a.getAttributeName());
                attributesAndVariantsRequest.add(a);
            });
            //lưu attribute
            List<AttributeResponse> savedAttribute = attributeService.saveListAttribute(listOfAttributeNames);
            //Lưu variant theo attribute
            List<AttributeAndVariantsResponse> listVariantOfAttrRs = variantService.checkAndSaveListVariants(attributesAndVariantsRequest);

            List<Attributes> listAttributes = new ArrayList<>();
            savedAttribute.forEach(a -> {
                Attributes attr = Attributes.builder()
                        .attributeName(a.getAttributeName())
                        .build();
                attr.setId(a.getId());
                listAttributes.add(attr);
            });

            //lưu product_attribute
            List<ProductAttributes> productAttributes = productAttributeService.saveListProductAttribute(listAttributes, savedEntity);
//            List<ProductAttributeResponse> productAttributeRs = productAttributes.stream()
//                    .map(productAttributeConverter :: toResponse).collect(Collectors.toList());
            List<Variants> variants = new ArrayList<>();

            // tìm list variant theo tên và attributeName
            listVariantOfAttrRs.forEach(variantOfAttribute -> variantOfAttribute.getVariantNames().forEach(variantName -> {
                Attributes attributes = attributeService.findByAttributeNameAndIsActiveTrue(variantOfAttribute.getAttributeName());
                Variants variantEntity = variantService.findByVariantNameAndAttributeIdAndIsActiveTrue(variantName,attributes.getId() );
                variants.add(variantEntity);
            }));

            // so sanh attributeId của hai bảng product_attribute và bảng variant để lưu bảng product_attribute_variant
            List<ProductAttributeVariantResponse> proAttrVarRs = productAttributeVariantService.save(productAttributes, variants);

            //lưu product combination
            List<ProductCombinationResponse> proComRs = productCombinationService.saveListCombinations(savedEntity, request.getCombinations());
            return productConverter.toResponse(savedEntity, thumbnailRespons, listVariantOfAttrRs, proComRs);
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
