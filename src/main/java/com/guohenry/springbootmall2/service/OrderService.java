package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.*;
import com.guohenry.springbootmall2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


public interface OrderService {


    void checkout(Member member, List<CartItem> cart);

    List<Order> getMemberOrders(int memberId);

    List<OrderItem> getOrderItems(int orderId);

    List<Order> getAllOrders();

}
