package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.CartService;
import com.guohenry.springbootmall2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ← 正確的 Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Controller
public class HomeController {


    @Autowired
    private ProductService productService;


    @Autowired
    private CartService cartService;


    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "1") int page, // 頁碼，預設第 1 頁
                       @RequestParam(required = false) String keyword, // 搜尋關鍵字（可選）
                       Model model) {

        // 設定每頁顯示幾筆商品
        int pageSize = 7;

        // 計算從第幾筆資料開始查詢（OFFSET）
        int offset = (page - 1) * pageSize;

        // 根據關鍵字與分頁參數查詢商品清單
        List<Product> products = productService.findByKeywordWithPaging(keyword, offset, pageSize);

        // 查詢符合關鍵字的商品總數（用來計算總頁數）
        int totalCount = productService.countByKeyword(keyword);

        // 計算總頁數（向上取整）
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 取得前三樣商品（不分頁）
       // List<Product> top3Products = productService.getTop3Products();

        // 把查詢結果與分頁參數塞進 model 給 Thymeleaf 頁面使用
        model.addAttribute("products", products);           // 本頁商品清單
        model.addAttribute("keyword", keyword);             // 搜尋欄保留關鍵字
        model.addAttribute("currentPage", page);            // 當前頁碼
        model.addAttribute("totalPages", totalPages);       // 總頁數


        return "home";
    }
}







