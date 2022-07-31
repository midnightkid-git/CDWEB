package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class OrderRequest {
    private String address;
    private String phoneNumber;
    private String status;
}
