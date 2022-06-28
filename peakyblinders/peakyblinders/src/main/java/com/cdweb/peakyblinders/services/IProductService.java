package com.cdweb.peakyblinders.services;

import com.cdweb.peakyblinders.models.Product;
import com.cdweb.peakyblinders.request.ProductRequest;
import com.cdweb.peakyblinders.response.ProductResponse;

import java.util.List;

public interface IProductService {
    public Product saveSize(ProductRequest productRequest);
    public List<ProductResponse> getAllProducts();
}
