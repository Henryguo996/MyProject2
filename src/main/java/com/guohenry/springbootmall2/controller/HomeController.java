package com.guohenry.springbootmall2.controller;


import ch.qos.logback.core.model.Model;
import com.guohenry.springbootmall2.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }



//    @GetMapping("/")
//    public String home(Model model, HttpSession session) {
//        List<Product> products = productService.findAll(); // 查詢所有商品
//        model.addAttribute("products", products);
//        int cartCount = cartService.getCartCount(session);
//        model.addAttribute("cartCount", cartCount);
//        return "home";
//    }

}
