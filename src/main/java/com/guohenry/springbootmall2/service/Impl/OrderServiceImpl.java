package com.guohenry.springbootmall2.service.Impl;

import com.guohenry.springbootmall2.model.CartItem;
import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.model.Order;
import com.guohenry.springbootmall2.model.OrderItem;
import com.guohenry.springbootmall2.repository.OrderRepository;
import com.guohenry.springbootmall2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
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

    @Override
    public List<Order> getMemberOrders(int memberId) {
        return orderRepository.findByMemberId(memberId);
    }

    @Override
    public List<OrderItem> getOrderItems(int orderId) {
        return orderRepository.findItemsByOrderId(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }
}
