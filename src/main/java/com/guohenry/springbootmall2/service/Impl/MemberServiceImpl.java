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

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void register(Member member) {
        member.setPassword(encoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    @Override
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && encoder.matches(password, member.getPassword())) {
            return member;
        }
        return null;
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(int id) {
        return memberRepository.findById(id);
    }

    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

}
