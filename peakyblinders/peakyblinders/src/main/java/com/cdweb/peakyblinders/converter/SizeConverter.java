package com.cdweb.peakyblinders.converter;

import com.cdweb.peakyblinders.models.Size;
import com.cdweb.peakyblinders.request.SizeRequest;
import com.cdweb.peakyblinders.response.SizeResponse;
import org.springframework.stereotype.Component;

@Component
public class SizeConverter {
    public Size toEntity(SizeRequest sizeRequest){
        return Size.builder().totalSize(sizeRequest.getTotalSize()).typeSize(sizeRequest.getTypeSize()).build();
    }
    public SizeResponse toRes(Size size){
        return SizeResponse.builder().sizeId(size.getSizeId()).totalSize(size.getTotalSize()).typeSize(size.getTypeSize()).build();
    }
}
