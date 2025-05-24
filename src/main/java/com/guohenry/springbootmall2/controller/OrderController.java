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

    // 顯示結帳頁面：GET /order/checkout
    @GetMapping("/order/checkout")
    public String checkoutPage(Model model, HttpSession session) {
        // 從 session 中取得登入會員資料
        Member member = (Member) session.getAttribute("member");

        // 若未登入，先記住原始跳轉路徑，轉向登入頁
        if (member == null) {
            session.setAttribute("redirectAfterLogin", "/order/checkout");
            return "redirect:/login";
        }

        // 取得購物車內容
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        // 若購物車為空或尚未建立，導回購物車頁面
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // 計算購物車總金額
        double total = cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // 傳入 cart 與 total 給 Thymeleaf 結帳頁面使用
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);

        // 返回結帳畫面（對應 checkout.html）
        return "checkout";
    }

    // 處理結帳送出：POST /order/checkout
    @PostMapping("/order/checkout")
    public String checkout(HttpSession session) {
        // 取得會員與購物車資料
        Member member = (Member) session.getAttribute("member");
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        // 驗證是否登入且購物車不為空
        if (member == null || cart == null || cart.isEmpty()) {
            // 錯誤流程回購物車
            return "redirect:/cart";
        }

        // 呼叫服務層進行結帳，建立訂單與訂單明細（寫入資料庫）
        orderService.checkout(member, cart);

        // 清空購物車（從 session 移除）
        session.removeAttribute("cart");

        // 結帳完成後導向訂單列表
        return "redirect:/order/list";
    }

    //  顯示目前登入會員的所有訂單：GET /order/list
    @GetMapping("/order/list")
    public String orderList(Model model, HttpSession session) {
        // 檢查是否登入
        Member member = (Member) session.getAttribute("member");
        if (member == null) return "redirect:/login";

        // 根據會員 ID 查詢訂單清單
        List<Order> orders = orderService.getMemberOrders(member.getId());

        // 傳入訂單清單給前端畫面顯示
        model.addAttribute("orders", orders);
        return "order-list"; // 對應 order-list.html
    }

    // 顯示某筆訂單的明細內容：GET /order/detail/{id}
    @GetMapping("/order/detail/{id}")
    public String orderDetail(@PathVariable int id, Model model) {
        // 根據訂單 ID 查詢該訂單的商品明細
        List<OrderItem> items = orderService.getOrderItems(id);

        // 傳入明細資料給前端畫面顯示
        model.addAttribute("items", items);
        return "order-detail"; // 對應 order-detail.html
    }
}
