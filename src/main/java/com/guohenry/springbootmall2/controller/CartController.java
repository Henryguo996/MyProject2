package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.CartItem;
import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.CartService;
import com.guohenry.springbootmall2.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class CartController {


    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;


    // 顯示購物車頁面
    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {

        // 從 Session 中取得購物車清單，若為 null 則建立空 ArrayList
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        // 計算購物車總金額（單價 * 數量）
        double total = cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // 將購物車資料、總金額與商品總數加進 model
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        model.addAttribute("cartCount", cart.stream().mapToInt(CartItem::getQuantity).sum());

        return "cart";
    }

    //  新增商品進購物車
    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable int id, // 路徑變數：商品 ID
                            @RequestParam(defaultValue = "1") int quantity, // 表單中傳入的購買數量，預設為 1
                            HttpSession session) {

        // 透過 Service 取得商品資料
        Product product = productService.getById(id);

        // 從 Session 取得購物車，如果沒有就初始化
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        boolean found = false;
        // 檢查購物車中是否已經有此商品
        for (CartItem item : cart) {
            if (item.getProduct().getId() == id) {
                // 若已存在，則數量加上新加入的數量
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        // 若購物車中尚未有此商品，則新增一筆項目
        if (!found) {
            // CartItem 需有 constructor: (Product, int)
            cart.add(new CartItem(product, quantity));
        }

        // 更新購物車回 Session 中（必須重新 set 生效）
        session.setAttribute("cart", cart);


        return "redirect:/cart";
    }

    //  從購物車中移除商品
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable int id, HttpSession session) {

        // 呼叫購物車服務進行移除
        cartService.removeFromCart(id, session);

        // 移除後導回購物車頁面
        return "redirect:/cart";
    }

    //  更新購物車商品數量
    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Map<String, String> params, HttpSession session) {

        // 傳入表單的參數（包含商品 id 與對應數量），呼叫服務層處理更新邏輯
        cartService.updateCart(params, session);

        // 更新完成後重新導回購物車頁面
        return "redirect:/cart";
    }

    //  回傳購物車總商品數量（用於右上角 icon 顯示）
    @GetMapping("/cart/count")
    @ResponseBody
    public int cartCount(HttpSession session) {

        // 呼叫 service 計算目前購物車中所有商品數量總和
        return cartService.getCartCount(session);
    }
}


