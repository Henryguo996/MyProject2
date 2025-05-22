package com.guohenry.springbootmall2.repository;

import com.guohenry.springbootmall2.model.Product;

import java.util.List;

public interface ProductRepository {


    List<Product> findByKeywordWithPaging(String keyword, int offset, int limit);

    int countByKeyword(String keyword);

    List<Product> findByNameContaining(String keyword);

    void save(Product product);

    List<Product> findAll();

    Product findById(int id);

    void update(Product product);

    void delete(int id);
}
