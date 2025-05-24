package com.guohenry.springbootmall2.controllerApi;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "商品 API", description = "提供商品查詢、新增、修改、刪除功能")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "取得所有商品")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "取得單一商品詳情")
    public Product getProduct(@PathVariable int id) {
        return productService.getById(id);
    }

    @PostMapping
    @Operation(summary = "新增商品")
    public String createProduct(@RequestBody Product product) {
        productService.save(product);
        return "商品已新增成功";
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品")
    public String updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        productService.update(product);
        return "商品已更新成功";
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除商品")
    public String deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return "商品已刪除成功";
    }
}
