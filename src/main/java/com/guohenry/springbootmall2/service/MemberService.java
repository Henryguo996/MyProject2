package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.Member;
import jakarta.validation.Valid;

import java.util.List;

public interface MemberService {

    Member findById(int id);

    void update(Member member); // 用於更新密碼等資訊

    void register(@Valid Member member);

    Member login(String email, String password);

    List<Member> findAll(); // 正確：統一為 List<Member>

    Member findByEmail(String email);
}
