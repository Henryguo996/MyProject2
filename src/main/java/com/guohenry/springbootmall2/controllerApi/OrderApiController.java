package com.guohenry.springbootmall2.controllerApi;

import com.guohenry.springbootmall2.model.Order;
import com.guohenry.springbootmall2.model.OrderItem;
import com.guohenry.springbootmall2.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "訂單 API", description = "查詢訂單清單與明細")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "取得指定會員的訂單清單")
    @GetMapping("/member/{memberId}")
    public List<Order> getMemberOrders(@PathVariable int memberId) {
        return orderService.getMemberOrders(memberId);
    }

    @Operation(summary = "取得訂單的明細內容")
    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(@PathVariable int orderId) {
        return orderService.getOrderItems(orderId);
    }
}
