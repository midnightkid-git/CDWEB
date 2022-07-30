package com.cdweb.backend.controllers.user;


import com.cdweb.backend.common.Constant;
import com.cdweb.backend.common.JwtService;
import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.requests.CartItemRequest;
import com.cdweb.backend.payloads.requests.ProductRequest;
import com.cdweb.backend.payloads.responses.CartResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.services.ICartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController(value="cartControllerOfUser")
@RequestMapping("/api/v1/user/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final JwtService jwtService;

    private  final ICartService cartService;

    @GetMapping("")
    ResponseEntity<?> getCart(HttpServletRequest request) {
         Users user = getUserFromRequest(request);
         List<CartResponse> listCartItem = cartService.findByUserId(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, listCartItem));
    }

    @PostMapping("")
    ResponseEntity<?> addItemToCart(HttpServletRequest httpServletRequest, @RequestBody CartItemRequest cartItemRequest ) {
        Users user = getUserFromRequest(httpServletRequest);
        List<CartResponse> listCartItem = cartService.findByUserId(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, listCartItem));
    }

    private Users getUserFromRequest(HttpServletRequest httpRequest) {
        String authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring(Constant.BEARER.length());
        Users user = jwtService.getUserFromToken(token);
        return user;
    }
}
