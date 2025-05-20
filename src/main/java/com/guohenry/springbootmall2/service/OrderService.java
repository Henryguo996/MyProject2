package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.*;
import com.guohenry.springbootmall2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void checkout(Member member, List<CartItem> cart) {
        double total = cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(total);

        int orderId = orderRepository.saveOrder(order);

        List<OrderItem> items = new ArrayList<>();
        for (CartItem cartItem : cart) {
            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setProductId(cartItem.getProduct().getId());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());
            items.add(item);
        }

        orderRepository.saveOrderItems(items);
    }

    public List<Order> getMemberOrders(int memberId) {
        return orderRepository.findByMemberId(memberId);
    }

    public List<OrderItem> getOrderItems(int orderId) {
        return orderRepository.findItemsByOrderId(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }

}
