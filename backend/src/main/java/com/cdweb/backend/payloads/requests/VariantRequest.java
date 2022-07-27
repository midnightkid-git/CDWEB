package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantRequest {
    private Long id;
    private Integer quantity;
    private Long attributeId;
}
