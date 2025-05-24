package com.guohenry.springbootmall2.controllerApi;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
@Tag(name = "首頁商品 API", description = "支援商品查詢、搜尋與分頁")
public class HomeApiController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "分頁查詢商品，可選擇關鍵字")
    @GetMapping
    public Map<String, Object> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword
    ) {
        int pageSize = 7;
        int offset = (page - 1) * pageSize;

        List<Product> products = productService.findByKeywordWithPaging(keyword, offset, pageSize);
        int totalCount = productService.countByKeyword(keyword);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        Map<String, Object> result = new HashMap<>();
        result.put("products", products);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("keyword", keyword);

        return result;
    }

    @Operation(summary = "取得前三樣熱銷商品")
    @GetMapping("/top3")
    public List<Product> getTop3Products() {
        return productService.getTop3Products(); // 你需要在 service 中實作此方法
    }
}
