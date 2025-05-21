package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.CartItem;
import com.guohenry.springbootmall2.model.Product;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface CartService {
    void addToCart(Product product, int quantity, HttpSession session);
    void removeFromCart(int productId, HttpSession session);
    void updateCart(Map<String, String> params, HttpSession session);
    List<CartItem> getCart(HttpSession session);
    double calculateTotal(List<CartItem> cart);
    int getCartCount(HttpSession session);
}
