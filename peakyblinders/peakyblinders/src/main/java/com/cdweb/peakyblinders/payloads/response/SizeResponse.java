package com.cdweb.peakyblinders.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SizeResponse {
    private int sizeId;
    private String typeSize;
    private int totalSize;

}
