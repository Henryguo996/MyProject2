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
// 設定所有方法的共用 URL 開頭為 /admin，例如：/admin/dashboard
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MemberService memberService;


    @Autowired
    private OrderService orderService;

    // 處理 GET 請求：/admin/dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        // 如果尚未登入，或登入者不是 ADMIN 權限，就導向登入頁
        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        // 若為管理員，顯示後台首頁畫面
        return "admin/admin-dashboard";
    }

    // 處理 GET 請求：/admin/members，顯示會員清單
    @GetMapping("/members")
    public String memberList(Model model, HttpSession session) {

        // 驗證是否為已登入管理員
        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        // 從資料庫查詢所有會員資料，傳給 view 顯示
        model.addAttribute("members", memberService.findAll());

        // 顯示 admin 會員列表頁
        return "admin/admin-member-list";
    }

    // 處理 GET 請求：/admin/orders，顯示所有訂單
    @GetMapping("/orders")
    public String orderList(Model model, HttpSession session) {

        // 驗證是否為已登入管理員
        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        // 從資料庫查詢所有訂單資料，傳給 view 顯示
        model.addAttribute("orders", orderService.getAllOrders());

        // 顯示 admin 訂單列表頁
        return "admin/admin-order-list";
    }

    // 處理 GET 請求：/admin/member-orders/{memberId}，顯示指定會員的訂單
    @GetMapping("/member-orders/{memberId}")
    public String ordersByMember(@PathVariable int memberId, Model model, HttpSession session) {
        // 驗證是否為已登入管理員
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

        //return user != null && "ADMIN".equals(user.getRole());
    }
}

