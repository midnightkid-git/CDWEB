package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSizeRequest {
    private Long size_id;
    private int quantity;
}
