package com.guohenry.springbootmall2.controllerApi;

import com.guohenry.springbootmall2.model.CartItem;
import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.service.CartService;
import com.guohenry.springbootmall2.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "購物車 API", description = "操作購物車：新增、更新、刪除、查詢")
public class CartApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Operation(summary = "取得購物車商品清單")
    @GetMapping
    public List<CartItem> getCart(HttpSession session) {
        return cartService.getCart(session);
    }

    @Operation(summary = "新增商品到購物車")
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable int id,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session) {
        Product product = productService.getById(id);
        cartService.addToCart(product, quantity, session);
        return "商品已加入購物車";
    }

    @Operation(summary = "從購物車移除商品")
    @DeleteMapping("/remove/{id}")
    public String removeFromCart(@PathVariable int id, HttpSession session) {
        cartService.removeFromCart(id, session);
        return "商品已從購物車移除";
    }

    @Operation(summary = "更新購物車商品數量")
    @PutMapping("/update")
    public String updateCart(@RequestBody Map<String, String> params, HttpSession session) {
        cartService.updateCart(params, session);
        return "購物車已更新";
    }

    @Operation(summary = "取得購物車總商品數量")
    @GetMapping("/count")
    public int getCartCount(HttpSession session) {
        return cartService.getCartCount(session);
    }

    @Operation(summary = "計算購物車總金額")
    @GetMapping("/total")
    public double getCartTotal(HttpSession session) {
        return cartService.calculateTotal(cartService.getCart(session));
    }
}
