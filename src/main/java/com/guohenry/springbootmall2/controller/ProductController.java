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

    // 顯示所有商品清單：GET /products
    @GetMapping("/products")
    public String productList(Model model) {
        // 呼叫 service 查詢所有商品，傳給 view 使用
        model.addAttribute("products", productService.getAll());

        // 對應到 templates/product-list.html
        return "product-list";
    }

    //  顯示單一商品詳情頁：GET /product/{id}
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable int id, Model model) {
        // 根據 id 查詢單一商品資料
        model.addAttribute("product", productService.getById(id));

        // 對應到 templates/product-detail.html
        return "product-detail";
    }

    // 顯示新增商品的表單頁面：GET /admin/product/new
    @GetMapping("/admin/product/new")
    public String newProductForm(Model model) {
        // 傳入一個空的 product 給 Thymeleaf 表單綁定
        model.addAttribute("product", new Product());

        // 對應到 templates/product-form.html（新增與編輯共用同一頁）
        return "product-form";
    }

    //  處理新增商品表單送出：POST /admin/product
    @PostMapping("/admin/product")
    public String saveProduct(@ModelAttribute Product product) {
        // 呼叫 service 儲存商品到資料庫
        productService.save(product);

        // 儲存完轉向商品清單
        return "redirect:/products";
    }

    //  顯示編輯商品的表單頁面：GET /admin/product/edit/{id}
    @GetMapping("/admin/product/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        // 查詢指定 id 的商品，傳入表單供修改
        model.addAttribute("product", productService.getById(id));

        // 共用 product-form.html 頁面
        return "product-form";
    }

    //  處理更新商品的表單送出：POST /admin/product/update
    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute Product product) {
        // 呼叫 service 執行商品資料更新
        productService.update(product);

        // 更新後導回商品列表
        return "redirect:/products";
    }

    // 刪除商品：GET /admin/product/delete/{id}
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        // 根據 id 刪除指定商品
        productService.delete(id);

        // 刪除後導回商品列表
        return "redirect:/products";
    }
}


