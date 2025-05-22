package com.guohenry.springbootmall2.repository.Impl;

import com.guohenry.springbootmall2.model.Order;
import com.guohenry.springbootmall2.model.OrderItem;
import com.guohenry.springbootmall2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO orders(member_id, order_date, total) VALUES(:memberId, :orderDate, :total)";
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", order.getMemberId());
        param.put("orderDate", order.getOrderDate());
        param.put("total", order.getTotal());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource(param), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void saveOrderItems(List<OrderItem> items) {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, price) VALUES(:orderId, :productId, :quantity, :price)";
        for (OrderItem item : items) {
            SqlParameterSource param = new BeanPropertySqlParameterSource(item);
            jdbcTemplate.update(sql, param);
        }
    }

    @Override
    public List<Order> findByMemberId(int memberId) {
        String sql = "SELECT * FROM orders WHERE member_id = :memberId ORDER BY order_date DESC";
        return jdbcTemplate.query(sql, Map.of("memberId", memberId),
                new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public List<OrderItem> findItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_item WHERE order_id = :orderId";
        return jdbcTemplate.query(sql, Map.of("orderId", orderId),
                new BeanPropertyRowMapper<>(OrderItem.class));
    }

    @Override
    public List<Order> findAllOrders() {
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
    }
}
