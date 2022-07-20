package com.cdweb.backend.payloads.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VariantResponse {
    private Long id;
    private String variantName;
    private Long attributeId;
}
