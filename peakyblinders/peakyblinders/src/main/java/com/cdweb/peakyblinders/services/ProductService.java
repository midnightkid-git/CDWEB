package com.cdweb.peakyblinders.services;

import com.cdweb.peakyblinders.converter.ProductConverter;
import com.cdweb.peakyblinders.converter.SizeConverter;
import com.cdweb.peakyblinders.models.Product;
import com.cdweb.peakyblinders.repositories.ProductRepository;
import com.cdweb.peakyblinders.repositories.SizeRepository;
import com.cdweb.peakyblinders.payloads.request.ProductRequest;
import com.cdweb.peakyblinders.payloads.response.ProductResponse;
import com.cdweb.peakyblinders.payloads.response.SizeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final SizeRepository sizeRepository;
    private final SizeConverter sizeConverter;

    public Product saveSize(ProductRequest productRequest){
        Product p = productRepository.save(productConverter.toEntity(productRequest));
        return p;
    }
    public List<ProductResponse> getAllProducts(){
        List<ProductResponse> productResponses = new ArrayList<>();
        productRepository.findAll().forEach(
                p->{
                    List<SizeResponse> sizeResponses = new ArrayList<>();
                    sizeRepository.findByProduct(p).forEach(s->{
                        sizeResponses.add(sizeConverter.toRes(s));
                    });
                    ProductResponse productResponse = productConverter.toRes(p);
                    productResponse.setSize(sizeResponses);
                    productResponses.add(productResponse);
                }
        );
        return productResponses;
    }
}
