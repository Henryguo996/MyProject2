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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //儲存一筆訂單資料（對應 orders 資料表）
    @Override
    public int saveOrder(Order order) {
        // 建立訂單新增的 SQL，使用命名參數 :xxx
        String sql = "INSERT INTO orders(member_id, order_date, total) " +
                "VALUES(:memberId, :orderDate, :total)";

        // 將訂單資料封裝進 Map，對應 SQL 中的參數
        Map<String, Object> param = new HashMap<>();
        param.put("memberId", order.getMemberId());
        param.put("orderDate", order.getOrderDate());
        param.put("total", order.getTotal());

        // KeyHolder 用來接收新增後資料的主鍵（自動產生的 order_id）
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 執行新增，並將主鍵放進 keyHolder 裡
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(param), keyHolder);

        // 回傳訂單主鍵（自動產生的 ID）
        return keyHolder.getKey().intValue();
    }

    // 儲存多筆訂單明細（對應 order_item 資料表）
    @Override
    public void saveOrderItems(List<OrderItem> items) {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, price) " +
                "VALUES(:orderId, :productId, :quantity, :price)";

        // 對每一筆訂單項目都執行一次 insert
        for (OrderItem item : items) {
            // 將物件屬性自動轉為命名參數
            SqlParameterSource param = new BeanPropertySqlParameterSource(item);
            namedParameterJdbcTemplate.update(sql, param);
        }
    }

    //查詢某位會員的所有訂單（依照日期降序排列）
    @Override
    public List<Order> findByMemberId(int memberId) {
        String sql = "SELECT * FROM orders WHERE member_id = :memberId ORDER BY order_date DESC";

        // 使用 Map.of 快速建立參數 Map，查詢並轉為 List<Order>
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("memberId", memberId),
                new BeanPropertyRowMapper<>(Order.class)
        );
    }

    //查詢某一筆訂單的所有明細（商品與數量）
    @Override
    public List<OrderItem> findItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_item WHERE order_id = :orderId";

        // 查詢明細，轉成 List<OrderItem>
        return namedParameterJdbcTemplate.query(
                sql,
                Map.of("orderId", orderId),
                new BeanPropertyRowMapper<>(OrderItem.class)
        );
    }

    //查詢所有訂單（用於後台管理），依照訂單日期排序
    @Override
    public List<Order> findAllOrders() {
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
    }
}
