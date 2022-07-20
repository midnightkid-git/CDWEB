package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantRequest {
    private Long id;
    private String variantName;
    private Long attributeId;
}
