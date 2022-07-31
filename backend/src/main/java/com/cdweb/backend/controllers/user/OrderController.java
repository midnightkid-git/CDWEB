package com.cdweb.backend.controllers.user;

import com.cdweb.backend.common.Constant;
import com.cdweb.backend.common.JwtService;
import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.requests.OrderRequest;
import com.cdweb.backend.payloads.responses.OrderResponse;
import com.cdweb.backend.payloads.responses.ResponseObject;
import com.cdweb.backend.services.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController(value="orderControllerOfUser")
@RequestMapping("/api/v1/user/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final JwtService jwtService;

    private final IOrderService orderService;

    @GetMapping("")
    ResponseEntity<?> getListOrderOfUser(HttpServletRequest httpServletRequest) {
        Users user = getUserFromRequest(httpServletRequest);
        List<OrderResponse> orderResponseList = orderService.getListOrderOfUserOrderResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, orderResponseList));
    }

    private Users getUserFromRequest(HttpServletRequest httpRequest) {
        String authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring(Constant.BEARER.length());
        Users user = jwtService.getUserFromToken(token);
        return user;
    }
}
