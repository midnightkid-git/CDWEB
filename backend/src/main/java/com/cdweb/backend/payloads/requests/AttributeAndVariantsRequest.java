package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AttributeAndVariantsRequest {
    private String attributeName;
    private List<String> variantNames;
}