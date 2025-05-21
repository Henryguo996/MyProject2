package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.CartService;
import com.guohenry.springbootmall2.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ← 正確的 Model
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // 所有商品
        List<Product> all = productService.getAll();
        model.addAttribute("products", all);

        model.addAttribute("cartCount", cartService.getCartCount(session));

        // 熱門前5商品（暫時用前五筆）
//        List<Product> hotProducts = all.size() >= 5 ? all.subList(0, 5) : all;
//        model.addAttribute("hotProducts", hotProducts);

        // 購物車數量（暫時假數字）
        model.addAttribute("cartCount", 0);

        return "home"; // 對應 home.html
    }
}




//    @GetMapping("/")
//    public String home(Model model, HttpSession session) {
//        List<Product> products = productService.findAll(); // 查詢所有商品
//        model.addAttribute("products", products);
//        int cartCount = cartService.getCartCount(session);
//        model.addAttribute("cartCount", cartCount);
//        return "home";
//    }


