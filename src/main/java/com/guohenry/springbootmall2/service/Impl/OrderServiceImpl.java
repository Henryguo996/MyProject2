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

    /**
     * 執行結帳邏輯：
     * 1. 建立 Order 訂單主資料（含會員 ID、訂單時間、總金額）
     * 2. 儲存訂單主檔並取得 orderId
     * 3. 將每個購物車項目轉為 OrderItem 並存入資料庫
     */

    @Override
    public void checkout(Member member, List<CartItem> cart) {
        // 計算購物車總金額：每項商品單價 * 數量，加總起來
        double total = cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // 建立訂單主資料
        Order order = new Order();
        order.setMemberId(member.getId());              // 設定訂購會員的 ID
        order.setOrderDate(LocalDateTime.now());        // 設定訂單時間為現在
        order.setTotal(total);                          // 設定訂單總金額

        // 儲存訂單主資料並回傳資料庫自動產生的 orderId
        int orderId = orderRepository.saveOrder(order);

        // 建立訂單明細清單
        List<OrderItem> items = new ArrayList<>();
        for (CartItem cartItem : cart) {
            OrderItem item = new OrderItem();
            item.setOrderId(orderId);                                  // 設定外鍵 orderId
            item.setProductId(cartItem.getProduct().getId());          // 商品 ID
            item.setQuantity(cartItem.getQuantity());                  // 購買數量
            item.setPrice(cartItem.getProduct().getPrice());           // 商品價格（單價）
            items.add(item);                                           // 加入清單中
        }

        // 一次性儲存所有訂單明細
        orderRepository.saveOrderItems(items);
    }

    /**
     * 查詢指定會員的所有訂單
     * @param memberId 會員 ID
     * @return 該會員的所有訂單
     */

    @Override
    public List<Order> getMemberOrders(int memberId) {

        return orderRepository.findByMemberId(memberId);
    }

    /**
     * 查詢某筆訂單的所有明細
     * @param orderId 訂單 ID
     * @return 該訂單的所有訂單明細項目
     */
    @Override
    public List<OrderItem> getOrderItems(int orderId) {

        return orderRepository.findItemsByOrderId(orderId);
    }

    /**
     * 查詢系統中所有訂單（管理員使用）
     * @return 全部訂單清單
     */
    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAllOrders();

    }
}
