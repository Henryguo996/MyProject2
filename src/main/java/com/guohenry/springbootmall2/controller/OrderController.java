package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.*;
import com.guohenry.springbootmall2.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/checkout")
    public String checkout(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (member == null || cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        orderService.checkout(member, cart);
        session.removeAttribute("cart");
        return "redirect:/order/list";
    }

    @GetMapping("/order/list")
    public String orderList(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) return "redirect:/login";

        List<Order> orders = orderService.getMemberOrders(member.getId());
        model.addAttribute("orders", orders);
        return "order-list";
    }

    @GetMapping("/order/detail/{id}")
    public String orderDetail(@PathVariable int id, Model model) {
        List<OrderItem> items = orderService.getOrderItems(id);
        model.addAttribute("items", items);
        return "order-detail";
    }
}
