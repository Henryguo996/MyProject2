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

    //  加入商品到購物車
    @Override
    public void addToCart(Product product, int quantity, HttpSession session) {
        List<CartItem> cart = getCart(session); // 從 session 取得購物車
        boolean found = false;

        for (CartItem item : cart) {
            // 如果購物車中已有此商品，就更新數量
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            // 沒有此商品就新增一筆
            cart.add(new CartItem(product, quantity));
        }

        // 更新 session 中的 cart 屬性
        session.setAttribute("cart", cart);
    }

    //  從購物車移除指定商品
    @Override
    public void removeFromCart(int productId, HttpSession session) {
        List<CartItem> cart = getCart(session);

        // removeIf：刪除符合條件的項目
        cart.removeIf(item -> item.getProduct().getId() == productId);

        session.setAttribute("cart", cart);
    }

    //  更新購物車內所有商品的數量
    @Override
    public void updateCart(Map<String, String> params, HttpSession session) {
        List<CartItem> cart = getCart(session);

        for (CartItem item : cart) {
            String key = "qty_" + item.getProduct().getId(); // 前端的欄位名稱
            if (params.containsKey(key)) {
                int qty = Integer.parseInt(params.get(key));
                item.setQuantity(qty); // 設定新數量
            }
        }

        session.setAttribute("cart", cart); // 更新 session
    }

    //  從 Session 中取得購物車，若不存在就初始化一個空的
    @Override
    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            // 初始化後塞入 session
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    //  計算整個購物車的總金額
    @Override
    public double calculateTotal(List<CartItem> cart) {
        return cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    //  計算購物車中所有商品的總數量
    @Override
    public int getCartCount(HttpSession session) {
        List<CartItem> cart = getCart(session);
        return cart.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
