package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.Member;
import jakarta.validation.Valid;

import java.util.List;


public interface MemberService {

     //根據會員 ID 查詢會員資料
    Member findById(int id);

     //更新會員資料（更新密碼、電話）
    void update(Member member);


     //註冊新會員
    void register(@Valid Member member);

     //登入驗證功能
    Member login(String email, String password);

     //查詢系統中所有會員（僅管理員使用）
    List<Member> findAll();

     // 根據 email 查詢會員（用於檢查註冊重複或登入驗證）
    Member findByEmail(String email);

}
