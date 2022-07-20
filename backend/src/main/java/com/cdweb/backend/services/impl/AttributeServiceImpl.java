package com.cdweb.backend.services.impl;

import com.cdweb.backend.converters.AttributeConverter;
import com.cdweb.backend.converters.VariantConverter;
import com.cdweb.backend.entities.Attributes;
import com.cdweb.backend.entities.Brands;
import com.cdweb.backend.entities.Products;
import com.cdweb.backend.entities.Variants;
import com.cdweb.backend.payloads.requests.AttributeAndVariantsRequest;
import com.cdweb.backend.payloads.requests.AttributeRequest;
import com.cdweb.backend.payloads.responses.AttributeAndVariantsResponse;
import com.cdweb.backend.payloads.responses.AttributeResponse;
import com.cdweb.backend.payloads.responses.ProductResponse;
import com.cdweb.backend.repositories.AttributeRepository;
import com.cdweb.backend.repositories.VariantRepository;
import com.cdweb.backend.services.IAttributeService;
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
public class AttributeServiceImpl implements IAttributeService {
    private final AttributeRepository attributeRepository;
    private final VariantRepository variantRepository;
    private final AttributeConverter attributeConverter;


    @Override
    public AttributeAndVariantsResponse save(AttributeAndVariantsRequest request) {
        log.info("Saving attribute{}", request.toString());
        Attributes entity = attributeRepository.findByAttributeNameAndIsActiveTrue(request.getAttributeName());
        if (entity == null && request.getAttributeName() != null) {
            Attributes newAttrEntity = Attributes.builder().attributeName(request.getAttributeName()).isActive(true).build();
            Attributes savedAttrEntity = attributeRepository.save(newAttrEntity);
            log.info("input{}",savedAttrEntity);
            AttributeAndVariantsResponse response = AttributeAndVariantsResponse.builder()
                    .attributeId(savedAttrEntity.getId())
                    .attributeName(savedAttrEntity.getAttributeName())
                    .build();
            List<String> variantNameResponse = new ArrayList<>();
            List<String> variantNameRequest = request.getVariantNames();
            if (variantNameRequest!=null) {
                for (String v : variantNameRequest ) {
                    if (v != null) {
                        Variants varEntity = variantRepository.findByVariantNameAndAttributeIdAndIsActiveTrue(v, savedAttrEntity.getId());
                        if (varEntity == null) {
                            Variants newEntity = Variants.builder()
                                    .variantName(v)
                                    .attribute(savedAttrEntity)
                                    .isActive(true)
                                    .build();
                            Variants savedEntity = variantRepository.save(newEntity);
                            variantNameResponse.add(savedEntity.getVariantName());
                        }
                    }
//                else {
//                    variantNameResponse.add(varEntity.getVariantName());
//                }
                }
                response.setVariantNames(variantNameResponse);
            } else {
                response.setVariantNames(null);
            }
            return response;
        }
        return null;
    }

    @Override
    public List<AttributeResponse> saveListAttribute(List<String> attributeNames) {
        List<AttributeResponse> response = new ArrayList<>();
        attributeNames.forEach(attributeName -> {
                    Attributes entity = attributeRepository.findByAttributeNameAndIsActiveTrue(attributeName);
                    if (entity == null) {
                        Attributes newEntity = Attributes.builder()
                                .attributeName(attributeName)
                                .isActive(true)
                                .build();
                        Attributes savedEntity = attributeRepository.save(newEntity);
                        response.add(attributeConverter.toResponse(savedEntity));
                    } else {
                        response.add(attributeConverter.toResponse(entity));
                    }

                }
        );
        return response;
    }


    @Override
    public AttributeResponse update(AttributeRequest request, Long id) {
        Attributes entity = attributeRepository.findByAttributeNameAndIsActiveTrue(request.getAttributeName());
        Attributes oldEntity = attributeRepository.findByIdAndIsActiveTrue(id);
        if (entity == null && oldEntity != null) {
            oldEntity.setAttributeName(request.getAttributeName());
            attributeRepository.save(oldEntity);
            return attributeConverter.toResponse(oldEntity);
        }
        return null;
    }

    @Override
    public List<AttributeResponse> findByIsActiveTrue() {
        return attributeRepository.findByIsActiveTrue().stream().map(attributeConverter::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AttributeAndVariantsResponse> findAllAttributeAndVariants(Pageable pageable) {
        List<AttributeAndVariantsResponse> response = new ArrayList<>();
        List<Attributes> attrs = attributeRepository.findByIsActiveTrueOrderByModifiedDateDesc(pageable).getContent();
       if (attrs.size() > 0) {
           attrs.forEach(a -> {
              if (a.isActive()){
                  List<Variants> variants = variantRepository.findByAttributeIdAndIsActiveTrue(a.getId());
                  List<String> variantNames = variants.stream().map((Variants::getVariantName)).collect(Collectors.toList());
                  AttributeAndVariantsResponse attrAndVar = AttributeAndVariantsResponse.builder()
                          .attributeId(a.getId())
                          .attributeName(a.getAttributeName())
                          .variantNames(variantNames)
                          .build();
                  response.add(attrAndVar);
              }
           });
       }
        return response;
    }

    @Override
    public List<AttributeAndVariantsResponse> findAllAttributeAndVariants() {
        List<AttributeAndVariantsResponse> response = new ArrayList<>();
        List<Attributes> attrs = attributeRepository.findByIsActiveTrue();
            attrs.forEach(a -> {
                if (a.isActive()){
                    List<Variants> variants = variantRepository.findByAttributeIdAndIsActiveTrue(a.getId());
                    List<String> variantNames = variants.stream().map((Variants::getVariantName)).collect(Collectors.toList());
                    AttributeAndVariantsResponse attrAndVar = AttributeAndVariantsResponse.builder()
                            .attributeId(a.getId())
                            .attributeName(a.getAttributeName())
                            .variantNames(variantNames)
                            .build();
                    response.add(attrAndVar);
                }
            });
        return response;
    }

    @Override
    public Attributes findByAttributeNameAndIsActiveTrue(String attributeName) {
        return attributeRepository.findByAttributeNameAndIsActiveTrue(attributeName);
    }


    @Override
    public List<AttributeResponse> findByProductIdAndIsActive(Long productId) {
        return attributeRepository.findByProductIdAndIsActive(productId)
                .stream().map(attributeConverter :: toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public int totalItem() {
        return (int) attributeRepository.countByIsActiveTrue();
    }

    @Override
    public boolean delete(Long[] ids) {
        boolean exists = true;
        for (Long id : ids) {
            if (!attributeRepository.existsByIdAndIsActiveTrue(id)) exists = false;
        }
        if (exists) {
            for (Long id :
                    ids) {
                Attributes entity = attributeRepository.findByIdAndIsActiveTrue(id);
                entity.setActive(false);
                attributeRepository.save(entity);
            }
        }
        return exists;
    }

    @Override
    public Boolean existsByNameAndIsActive(String attributeName) {
        return attributeRepository.existsByAttributeNameAndIsActiveTrue(attributeName);
    }
}
