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

    //暫時加入 debug API
    @GetMapping("/api/cart")
    @ResponseBody
    public List<CartItem> getCart(HttpSession session) {
        return (List<CartItem>) session.getAttribute("cart");
    }


    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        double total = cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        model.addAttribute("cartCount", cart.stream().mapToInt(CartItem::getQuantity).sum());

        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable int id,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session) {

        Product product = productService.getById(id); // 非 null
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId() == id) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(product, quantity)); // 確保 CartItem 有正確 constructor
        }

        session.setAttribute("cart", cart); // 必須寫回 session
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable int id, HttpSession session) {
        cartService.removeFromCart(id, session);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Map<String, String> params, HttpSession session) {
        cartService.updateCart(params, session);
        return "redirect:/cart";
    }

    @GetMapping("/cart/count")
    @ResponseBody
    public int cartCount(HttpSession session) {
        return cartService.getCartCount(session);
    }
}
