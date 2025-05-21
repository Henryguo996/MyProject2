package com.guohenry.springbootmall2.service.Impl;

import com.guohenry.springbootmall2.model.CartItem;
import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    @Override
    public void addToCart(Product product, int quantity, HttpSession session) {
        List<CartItem> cart = getCart(session);
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(new CartItem(product, quantity));
        }
        session.setAttribute("cart", cart);
    }

    @Override
    public void removeFromCart(int productId, HttpSession session) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getProduct().getId() == productId);
        session.setAttribute("cart", cart);
    }

    @Override
    public void updateCart(Map<String, String> params, HttpSession session) {
        List<CartItem> cart = getCart(session);
        for (CartItem item : cart) {
            String key = "qty_" + item.getProduct().getId();
            if (params.containsKey(key)) {
                int qty = Integer.parseInt(params.get(key));
                item.setQuantity(qty);
            }
        }
        session.setAttribute("cart", cart);
    }

    @Override
    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    public double calculateTotal(List<CartItem> cart) {
        return cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public int getCartCount(HttpSession session) {
        List<CartItem> cart = getCart(session);
        return cart.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
