package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.*;
import com.guohenry.springbootmall2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


public interface OrderService {

    /**
     結帳流程：
     1. 建立訂單主資料（含會員、總金額、訂單時間）
     2. 建立訂單明細（每筆購物車商品）
     3. 儲存至資料庫
     */
    void checkout(Member member, List<CartItem> cart);

     //查詢指定會員的所有訂單
    List<Order> getMemberOrders(int memberId);

     //查詢某筆訂單的所有訂單明細
    List<OrderItem> getOrderItems(int orderId);

     //查詢系統中所有訂單（通常為後台管理員使用）
    List<Order> getAllOrders();
}