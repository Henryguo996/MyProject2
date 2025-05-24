package com.guohenry.springbootmall2.repository;

import com.guohenry.springbootmall2.model.Product;

import java.util.List;

//  ProductRepository 是商品資料存取層 DAO 的介面，提供對 product 資料表的操作
public interface ProductRepository {

    //  關鍵字搜尋 + 分頁查詢（首頁使用）
    List<Product> findByKeywordWithPaging(String keyword, int offset, int limit);

    //  計算符合關鍵字的商品總數（用於分頁）
    int countByKeyword(String keyword);

    //  模糊搜尋商品名稱（不含分頁）
    List<Product> findByNameContaining(String keyword);

    //  新增商品
    void save(Product product);

    // 查詢全部商品（不分頁）
    List<Product> findAll();

    //  根據商品 ID 查詢單筆資料
    Product findById(int id);

    //  更新商品資料
    void update(Product product);

    //  刪除商品（根據 ID）
    void delete(int id);
}
