package com.guohenry.springbootmall2.service.Impl;

import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.repository.MemberRepository;
import com.guohenry.springbootmall2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberServiceImpl implements MemberService {


    @Autowired
    private MemberRepository memberRepository;

    //  BCrypt 密碼編碼器，專門用來加密與比對密碼（安全性最佳實務）
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    //  處理註冊邏輯（密碼加密後儲存進資料庫）
    @Override
    public void register(Member member) {
        // 檢查是否已存在相同 email
//        Member existing = memberRepository.findByEmail(member.getEmail());
//        if (existing != null) {
//            throw new RuntimeException("此 Email 已被註冊");
//        }
        // 加密使用者輸入的原始密碼（不可明碼儲存）
        member.setPassword(encoder.encode(member.getPassword()));

        // 儲存新會員資料
        memberRepository.save(member);
    }

    //  處理登入邏輯
    @Override
    public Member login(String email, String password) {
        // 先查詢該 email 的會員資料
        Member member = memberRepository.findByEmail(email);

        // 若會員存在，且輸入的密碼與加密密碼相符，則登入成功
        if (member != null && encoder.matches(password, member.getPassword())) {
            return member;
        }

        // 登入失敗，回傳 null
        return null;
    }

    //  查詢所有會員（for 管理員後台使用）
    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //  根據 ID 查詢會員
    @Override
    public Member findById(int id) {
        return memberRepository.findById(id);
    }

    //  更新會員（目前主要用於更新密碼）
    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }

    //  根據 Email 查詢會員（for 註冊檢查 / 登入）
    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
