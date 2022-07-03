package com.cdweb.peakyblinders.services;

import com.cdweb.peakyblinders.converter.SizeConverter;
import com.cdweb.peakyblinders.models.Product;
import com.cdweb.peakyblinders.models.Size;
import com.cdweb.peakyblinders.repositories.SizeRepository;
import com.cdweb.peakyblinders.payloads.response.SizeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SizeService {
    private final SizeRepository sizeRepository;
    private final SizeConverter sizeConverter;
    public SizeResponse saveSize(Size size, Product p){
        Size sizeEntity = sizeRepository.save(Size.builder().typeSize(size.getTypeSize()).totalSize(size.getTotalSize()).product(p).build());
        return sizeConverter.toRes(sizeEntity);
    }


}
