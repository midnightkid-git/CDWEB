package com.cdweb.backend.converters;

import com.cdweb.backend.entities.Variants;
import com.cdweb.backend.payloads.requests.VariantRequest;
import com.cdweb.backend.payloads.responses.VariantResponse;
import org.springframework.stereotype.Component;

@Component
public class VariantConverter {
    public Variants toEntity(VariantRequest request){
        return Variants.builder()
                .variantName(request.getVariantName())
                .build();
    }

    public VariantResponse toResponse(Variants entity){
        return VariantResponse.builder()
                .id(entity.getId())
                .variantName(entity.getVariantName())
                .build();
    }
}
