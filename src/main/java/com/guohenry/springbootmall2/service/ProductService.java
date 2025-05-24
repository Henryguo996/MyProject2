package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.Product;

import java.util.List;



public interface ProductService {

     //分頁查詢：依照關鍵字查詢商品並進行分頁
    List<Product> findByKeywordWithPaging(String keyword, int offset, int limit);

     //計算符合關鍵字的商品數量（用於分頁總頁數）
    int countByKeyword(String keyword);

     //依商品名稱關鍵字搜尋商品（無分頁）
    List<Product> searchByName(String keyword);

     //儲存商品（新增）
    void save(Product product);

     //取得所有商品（無條件）
    List<Product> getAll();

     //根據商品 ID 查詢商品
    Product getById(int id);

     //更新商品內容
    void update(Product product);

     //根據 ID 刪除商品
    void delete(int id);
}
