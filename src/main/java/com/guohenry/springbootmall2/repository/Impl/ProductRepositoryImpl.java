package com.guohenry.springbootmall2.repository.Impl;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 查詢商品（可搜尋關鍵字 + 分頁功能）
    @Override
    public List<Product> findByKeywordWithPaging(String keyword, int offset, int limit) {

        String sql = "SELECT * FROM product WHERE name LIKE :keyword LIMIT :limit OFFSET :offset";

        // 建立查詢參數
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("keyword", "%" + (keyword == null ? "" : keyword) + "%");
        params.addValue("offset", offset);  // 從第幾筆開始查
        params.addValue("limit", limit);    // 查幾筆

        // 執行查詢並自動將結果轉成 List<Product>
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Product.class));
    }

    //前三張熱賣商品
    @Override
    public List<Product> findTop3() {
        String sql = "SELECT * FROM product ORDER BY product_id DESC LIMIT 3";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }


    //  查詢符合關鍵字的商品總筆數（用於分頁）
    @Override
    public int countByKeyword(String keyword) {
        String sql = "SELECT COUNT(*) FROM product WHERE name LIKE :keyword";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("keyword", "%" + (keyword == null ? "" : keyword) + "%");

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    //  模糊搜尋商品名稱（不分頁）
    @Override
    public List<Product> findByNameContaining(String keyword) {
        String sql = "SELECT * FROM product WHERE name LIKE :keyword";

        // 組出 %關鍵字%
        String pattern = "%" + keyword + "%";
        MapSqlParameterSource params = new MapSqlParameterSource("keyword", pattern);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Product.class));
    }

    //  新增一筆商品資料
    @Override
    public void save(Product product) {
        String sql = "INSERT INTO product(name, description, price, image_url) " +
                "VALUES(:name, :description, :price, :imageUrl)";

        // 將 product 欄位與命名參數自動對應
        SqlParameterSource param = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update(sql, param);
    }

    //  查詢所有商品（不分頁、不排序）
    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    //  根據 ID 查詢單一商品
    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM product WHERE id = :id";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(Product.class)
        );
    }

    //  更新商品資料（根據 id）
    @Override
    public void update(Product product) {
        String sql = "UPDATE product SET name=:name, description=:description, " +
                "price=:price, image_url=:imageUrl WHERE id=:id";

        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        namedParameterJdbcTemplate.update(sql, param);
    }

    //  刪除商品（根據 id）
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM product WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }
}
