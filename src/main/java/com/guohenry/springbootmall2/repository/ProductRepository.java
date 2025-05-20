package com.guohenry.springbootmall2.repository;

import com.guohenry.springbootmall2.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void save(Product product) {
        String sql = "INSERT INTO product(name, description, price, image_url) VALUES(:name, :description, :price, :imageUrl)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, param);
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM product WHERE id = :id";
        return jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(Product.class));
    }

    public void update(Product product) {
        String sql = "UPDATE product SET name=:name, description=:description, price=:price, image_url=:imageUrl WHERE id=:id";
        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, param);
    }

    public void delete(int id) {
        String sql = "DELETE FROM product WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }
}
