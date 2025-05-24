package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.service.MemberService;
import com.guohenry.springbootmall2.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MemberService memberService;


    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        //  驗證是否為已登入管理員
        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        // 顯示後台首頁畫面
        return "admin/admin-dashboard";
    }

    //顯示會員清單
    @GetMapping("/members")
    public String memberList(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        // 從資料庫查詢所有會員資料，傳給 view 顯示
        model.addAttribute("members", memberService.findAll());

        // 顯示 會員列表頁
        return "admin/admin-member-list";
    }

    //顯示所有訂單
    @GetMapping("/orders")
    public String orderList(Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        model.addAttribute("orders", orderService.getAllOrders());

        // 顯示訂單列表頁
        return "admin/admin-order-list";
    }

    // 顯示指定會員的訂單
    @GetMapping("/member-orders/{memberId}")
    public String ordersByMember(@PathVariable int memberId, Model model, HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        // 根據會員 ID 查詢該會員的訂單，傳給 view 顯示
        model.addAttribute("orders", orderService.getMemberOrders(memberId));

        // 顯示該會員的訂單列表頁
        return "admin/admin-member-orders";
    }

    //管理員權限檢查
    private boolean isAdmin(HttpSession session) {
        Member user = (Member) session.getAttribute("member");
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());

    }
}

