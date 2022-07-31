package com.cdweb.backend.controllers.admin;

import com.cdweb.backend.common.Constant;
import com.cdweb.backend.common.JwtService;
import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.requests.ProductRequest;
import com.cdweb.backend.payloads.requests.UpdateOrderStatusRequest;
import com.cdweb.backend.payloads.responses.OrderResponse;
import com.cdweb.backend.payloads.responses.ProductResponse;
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

@RestController(value="ordersControllerOfAdmin")
@RequestMapping("/api/v1/admin/order")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {

    private final IOrderService orderService;
    private final JwtService jwtService;

    @GetMapping("")
    ResponseEntity<?> getListOrder(HttpServletRequest httpServletRequest) {
        Users user = getUserFromRequest(httpServletRequest);
        List<OrderResponse> orderResponseList = orderService.getListOrder();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Success", null, orderResponseList));
    }
    @PutMapping("/{id}")
    ResponseEntity<?> updateStatus(HttpServletRequest httpServletRequest, @RequestBody UpdateOrderStatusRequest request, @PathVariable("id") Long id) {
        Users user = getUserFromRequest(httpServletRequest);
        OrderResponse orderResponse = orderService.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update status Successfully", true));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getOrderById(HttpServletRequest httpServletRequest, @PathVariable("id") Long id) {
        Users user = getUserFromRequest(httpServletRequest);
        OrderResponse orderResponse = orderService.getOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok",null, orderResponse));
    }
    private Users getUserFromRequest(HttpServletRequest httpRequest) {
        String authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring(Constant.BEARER.length());
        Users user = jwtService.getUserFromToken(token);
        return user;
    }

}
