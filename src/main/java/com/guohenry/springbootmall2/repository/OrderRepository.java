package com.guohenry.springbootmall2.repository;

import com.guohenry.springbootmall2.model.Order;
import com.guohenry.springbootmall2.model.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository {



    int saveOrder(Order order);

    void saveOrderItems(List<OrderItem> items);

    List<Order> findByMemberId(int memberId);

    List<OrderItem> findItemsByOrderId(int orderId);

    List<Order> findAllOrders();

}
