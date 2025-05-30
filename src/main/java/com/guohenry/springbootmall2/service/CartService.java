package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.CartItem;
import com.guohenry.springbootmall2.model.Product;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface CartService {


     //將商品加入購物車
    void addToCart(Product product, int quantity, HttpSession session);

    //從購物車移除商品
    void removeFromCart(int productId, HttpSession session);

     // 更新購物車內容
    void updateCart(Map<String,String> params, HttpSession session);

     //取得目前購物車的所有項目
    List<CartItem> getCart(HttpSession session);

     //計算購物車總金額
    double calculateTotal(List<CartItem> cart);

     //取得購物車總數量（通常用來顯示在右上角購物車圖示）
    int getCartCount(HttpSession session);
}
