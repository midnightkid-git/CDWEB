package com.cdweb.peakyblinders.request;

import com.cdweb.peakyblinders.models.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequest {
    private String productName;
    private int price;
    private int categoryId;
    private String categoryName;
//    private List<String> thumbnails;
    List<SizeRequest> sizes;

}
