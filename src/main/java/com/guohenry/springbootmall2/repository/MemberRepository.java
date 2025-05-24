package com.guohenry.springbootmall2.repository;

import com.guohenry.springbootmall2.model.Member;

import java.util.List;


public interface MemberRepository {

    //  根據會員 ID 查詢會員資料
    Member findById(int id);

    //  更新會員資料（目前主要用於變更密碼）
    void update(Member member);

    //  查詢所有會員資料（常用於後台管理）
    List<Member> findAll();

    //  儲存新會員（註冊用）
    void save(Member member);

    //  根據 Email 查詢會員（登入與註冊時檢查是否已存在）
    Member findByEmail(String email);
}
