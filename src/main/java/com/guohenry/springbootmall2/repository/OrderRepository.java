package com.guohenry.springbootmall2.repository;

import com.guohenry.springbootmall2.model.Order;
import com.guohenry.springbootmall2.model.OrderItem;

import java.util.*;



public interface OrderRepository {

    //  儲存一筆訂單主資料，回傳該筆訂單的自動生成 ID
    int saveOrder(Order order);

    //  儲存多筆訂單明細資料（商品、數量、價格）
    void saveOrderItems(List<OrderItem> items);

    //  根據會員 ID 查詢所有訂單（依時間排序）
    List<Order> findByMemberId(int memberId);

    //  根據訂單 ID 查詢該訂單所有明細
    List<OrderItem> findItemsByOrderId(int orderId);

    //  查詢所有訂單（for 後台使用）
    List<Order> findAllOrders();
}
