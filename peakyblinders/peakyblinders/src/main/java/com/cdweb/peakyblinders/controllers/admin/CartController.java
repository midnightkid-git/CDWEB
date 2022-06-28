package com.cdweb.peakyblinders.controllers.admin;

import com.cdweb.peakyblinders.converter.CartConverter;
import com.cdweb.peakyblinders.converter.ProductInCartConverter;
import com.cdweb.peakyblinders.models.Cart;
import com.cdweb.peakyblinders.request.CartRequest;
import com.cdweb.peakyblinders.response.*;
import com.cdweb.peakyblinders.services.CartService;
import com.cdweb.peakyblinders.services.ICartService;
import com.cdweb.peakyblinders.services.ProductInCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController(value = "Cart")
@RequestMapping(path = "/api/v1/Cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {
    private final ProductInCartService productInCartService;
    private final ProductInCartConverter productInCartConverter;
    private final ICartService cartService;
    private final CartConverter cartConverter;
    @GetMapping("")
        // this request is : http://localhost:8081/api/v1/Cart
    List<CartResponse> getAlLCartItem(){
        return cartService.getAllCartItems();
    }

    @PostMapping("/insert")
    ResponseEntity<?> insertCartItem (@RequestBody CartRequest request){
        Cart c = cartService.saveCart(request);
        List<ProductInCartResponse> productInCartResponses = new ArrayList<>();

        request.getProductInCarts().forEach(pic->{
            productInCartResponses.add(productInCartService.saveProductInCart(productInCartConverter.toEntity(pic),c));
        });
        CartResponse cartResponse = cartConverter.toRes(c);
        cartResponse.setProductInCarts(productInCartResponses);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","insert successfully",cartResponse)
        );
    }
}
