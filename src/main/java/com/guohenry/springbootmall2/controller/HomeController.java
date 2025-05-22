package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.CartService;
import com.guohenry.springbootmall2.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ← 正確的 Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(required = false) String keyword,
                       Model model) {

        int pageSize = 7;
        int offset = (page - 1) * pageSize;

        List<Product> products = productService.findByKeywordWithPaging(keyword, offset, pageSize);
        int totalCount = productService.countByKeyword(keyword); // 查詢符合關鍵字的總商品數

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "home";
    }

}


//    @GetMapping("/")
//    public String home(@RequestParam(required = false) String keyword,
//                       Model model,
//                       HttpSession session) {
//
//        List<Product> products;
//
//        if (keyword != null && !keyword.isBlank()) {
//            products = productService.searchByName(keyword); // 搜尋商品名稱
//        } else {
//            products = productService.getAll(); // 顯示全部
//        }
//
//        model.addAttribute("products", products);
//        model.addAttribute("keyword", keyword); // 為了保留搜尋欄輸入值
//        model.addAttribute("cartCount", cartService.getCartCount(session));
//
//        // 熱門前5商品（暫時用前五筆）
////        List<Product> hotProducts = all.size() >= 5 ? all.subList(0, 5) : all;
////        model.addAttribute("hotProducts", hotProducts);
//
//        // 購物車數量（暫時假數字）
//
//        return "home"; // Thymeleaf 對應 home.html
//    }
//
//}





