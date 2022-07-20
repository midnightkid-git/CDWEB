package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmRequest {
    private Long userId;
    private String otpCode;
}
