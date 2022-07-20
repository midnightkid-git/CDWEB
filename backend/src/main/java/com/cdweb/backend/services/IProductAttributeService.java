package com.cdweb.backend.services;

import com.cdweb.backend.entities.Attributes;
import com.cdweb.backend.entities.ProductAttributes;
import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.requests.AttributeRequest;
import com.cdweb.backend.payloads.responses.ProductAttributeResponse;
import com.cdweb.backend.repositories.AttributeRepository;

import java.util.List;

public interface IProductAttributeService {

    List<ProductAttributes> saveListProductAttribute(List<Attributes> attributes, Products product);
}
