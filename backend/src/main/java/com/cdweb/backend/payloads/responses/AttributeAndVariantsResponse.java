package com.cdweb.backend.payloads.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AttributeAndVariantsResponse {
    private Long attributeId;
    private String attributeName;
    private List<String> variantNames;
}
