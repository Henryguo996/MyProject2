package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // 全部商品
    @GetMapping("/products")
    public String productList(Model model) {
        model.addAttribute("products", productService.getAll());
        return "product-list";
    }

    // 單一商品
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product-detail";
    }

    // 新增商品
    @GetMapping("/admin/product/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    // 儲存商品
    @PostMapping("/admin/product")
    public String saveProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    // 編輯商品
    @GetMapping("/admin/product/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product-form";
    }

    // 更新商品
    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute Product product) {
        productService.update(product);
        return "redirect:/products";
    }

    // 刪除商品
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
