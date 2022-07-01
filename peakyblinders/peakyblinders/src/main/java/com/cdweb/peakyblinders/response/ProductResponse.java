package com.cdweb.peakyblinders.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductResponse {
    private int productId;
    private String productName;
    private int price;
    private int categoryId;
    private String categoryName;
//    private List<String> thumbnails;
    private List<SizeResponse> size;

}
