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

    // 顯示所有商品清單
    @GetMapping("/products")
    public String productList(Model model) {
        // 呼叫 service 查詢所有商品，傳給 view 使用
        model.addAttribute("products", productService.getAll());


        return "product-list";
    }

    //  顯示單一商品詳情頁
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable int id, Model model) {
        // 根據 id 查詢單一商品資料
        model.addAttribute("product", productService.getById(id));


        return "product-detail";
    }

    // 顯示新增商品的表單頁面
    @GetMapping("/admin/product/new")
    public String newProductForm(Model model) {

        // 傳入一個空的 product 給 Thymeleaf 表單綁定
        model.addAttribute("product", new Product());

        return "product-form";
    }

    //  處理新增商品表單送出
    @PostMapping("/admin/product")
    public String saveProduct(@ModelAttribute Product product) {
        // 呼叫 service 儲存商品到資料庫
        productService.save(product);


        return "redirect:/products";
    }

    //  顯示編輯商品的表單頁面
    @GetMapping("/admin/product/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        // 查詢指定 id 的商品，傳入表單供修改
        model.addAttribute("product", productService.getById(id));


        return "product-form";
    }

    //  處理更新商品的表單送出
    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute Product product) {
        // 呼叫 service 執行商品資料更新
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


