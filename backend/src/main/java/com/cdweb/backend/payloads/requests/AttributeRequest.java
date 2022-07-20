package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeRequest {
    private Long id;
    private String attributeName;
}
