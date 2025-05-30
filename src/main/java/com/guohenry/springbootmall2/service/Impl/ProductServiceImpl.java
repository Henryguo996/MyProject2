package com.guohenry.springbootmall2.service.Impl;

import com.guohenry.springbootmall2.model.Product;
import com.guohenry.springbootmall2.repository.ProductRepository;
import com.guohenry.springbootmall2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    //前三張熱賣商品
    @Override
    public List<Product> getTop3Products() {
        return productRepository.findTop3(); // 呼叫 repository
    }


    /**
     * 關鍵字搜尋商品（分頁版）
     * @param keyword 搜尋關鍵字
     * @param offset 跳過多少筆（例如 (page-1)*size）
     * @param limit 每頁顯示幾筆
     * @return 商品列表（符合條件）
     */
    @Override
    public List<Product> findByKeywordWithPaging(String keyword, int offset, int limit) {
        return productRepository.findByKeywordWithPaging(keyword, offset, limit);
    }

    /**
     * 統計關鍵字搜尋結果的總數（用於分頁總頁數計算）
     * @param keyword 關鍵字
     * @return 總筆數
     */
    @Override
    public int countByKeyword(String keyword) {

        return productRepository.countByKeyword(keyword);

    }

    /**
     * 依名稱關鍵字搜尋商品（未分頁）
     * @param keyword 關鍵字
     * @return 商品列表
     */
    @Override
    public List<Product> searchByName(String keyword) {

        return productRepository.findByNameContaining(keyword);

    }

    /**
     * 儲存一筆商品資料
     * @param product 商品資料物件
     */
    @Override
    public void save(Product product) {

        productRepository.save(product);
    }

    /**
     * 取得所有商品
     * @return 商品列表
     */
    @Override
    public List<Product> getAll() {

        return productRepository.findAll();

    }

    /**
     * 根據商品 ID 查詢單一商品
     * @param id 商品 ID
     * @return 商品物件
     */
    @Override
    public Product getById(int id) {

        return productRepository.findById(id);

    }

    /**
     * 更新商品資料
     * @param product 商品物件
     */
    @Override
    public void update(Product product) {

        productRepository.update(product);

    }

    /**
     * 根據商品 ID 刪除商品
     * @param id 商品 ID
     */
    @Override
    public void delete(int id) {

        productRepository.delete(id);

    }
}
