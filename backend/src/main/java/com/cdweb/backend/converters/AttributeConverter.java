package com.cdweb.backend.converters;

import com.cdweb.backend.entities.Attributes;
import com.cdweb.backend.payloads.requests.AttributeRequest;
import com.cdweb.backend.payloads.responses.AttributeResponse;
import org.springframework.stereotype.Component;

@Component
public class AttributeConverter {
    public Attributes toEntity(AttributeRequest request){
        return Attributes.builder()
                .attributeName(request.getAttributeName())
                .build();
    }


    public AttributeResponse toResponse(Attributes entity){
        return AttributeResponse.builder()
                .id(entity.getId())
                .attributeName(entity.getAttributeName())
                .build();
    }
}
