package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.Product;

import java.util.List;


public interface ProductService {

    List<Product> searchByName(String keyword);

    void save(Product product);

    List<Product> getAll();

    Product getById(int id);

    void update(Product product);

    void delete(int id);
}
