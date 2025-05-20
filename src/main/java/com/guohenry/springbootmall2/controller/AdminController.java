package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.model.Order;
import com.guohenry.springbootmall2.service.MemberService;
import com.guohenry.springbootmall2.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        Member user = (Member) session.getAttribute("member");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        return "admin-dashboard";
    }

    @GetMapping("/members")
    public String memberList(Model model, HttpSession session) {
        Member user = (Member) session.getAttribute("member");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";
        model.addAttribute("members", memberService.findAll());
        return "admin-member-list";
    }

    @GetMapping("/orders")
    public String orderList(Model model, HttpSession session) {
        Member user = (Member) session.getAttribute("member");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin-order-list";
    }

    @GetMapping("/member-orders/{memberId}")
    public String ordersByMember(@PathVariable int memberId, Model model, HttpSession session) {
        Member user = (Member) session.getAttribute("member");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";
        model.addAttribute("orders", orderService.getMemberOrders(memberId));
        return "admin-member-orders";
    }
}
