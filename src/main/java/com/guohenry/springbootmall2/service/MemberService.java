package com.guohenry.springbootmall2.service;

import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void register(Member member) {
        member.setPassword(encoder.encode(member.getPassword()));
        member.setRole("USER");
        memberRepository.save(member);
    }

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && encoder.matches(password, member.getPassword())) {
            return member;
        }
        return null;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }


}
