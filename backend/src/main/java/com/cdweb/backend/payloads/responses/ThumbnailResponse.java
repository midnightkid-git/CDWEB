package com.cdweb.backend.payloads.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ThumbnailResponse {
    private Long id;
    private Long productId;
    private String imageLink;
}
