package com.guohenry.springbootmall2.repository.Impl;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Product> findByNameContaining(String keyword) {
        String sql = "SELECT * FROM product WHERE name LIKE :keyword";
        String pattern = "%" + keyword + "%";
        MapSqlParameterSource params = new MapSqlParameterSource("keyword", pattern);

        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Product.class));
    }



    @Override
    public void save(Product product) {
        String sql = "INSERT INTO product(name, description, price, image_url) VALUES(:name, :description, :price, :imageUrl)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM product WHERE id = :id";
        return jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE product SET name=:name, description=:description, price=:price, image_url=:imageUrl WHERE id=:id";
        SqlParameterSource param = new BeanPropertySqlParameterSource(product);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM product WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }
}
