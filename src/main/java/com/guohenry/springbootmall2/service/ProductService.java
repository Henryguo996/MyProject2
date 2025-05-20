package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(int id) {
        return productRepository.findById(id);
    }

    public void update(Product product) {
        productRepository.update(product);
    }

    public void delete(int id) {
        productRepository.delete(id);
    }
}
