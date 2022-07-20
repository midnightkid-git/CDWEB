package com.cdweb.backend.converters;

import com.cdweb.backend.entities.Thumbnails;
import com.cdweb.backend.payloads.responses.ThumbnailResponse;
import org.springframework.stereotype.Component;

@Component
public class ThumbnailConverter {
    public ThumbnailResponse toResponse(Thumbnails entity){
        return ThumbnailResponse
                .builder()
                    .id(entity.getId())
                    .productId(entity.getProduct().getId())
                    .imageLink(entity.getImageLink())
                .build();
    }
}
