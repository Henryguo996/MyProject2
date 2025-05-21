package com.guohenry.springbootmall2.repository;

import com.guohenry.springbootmall2.model.Member;

import java.util.List;


import com.guohenry.springbootmall2.model.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public interface MemberRepository {

    Member findById(int id);

    void update(Member member);

    List<Member> findAll();

    void save(Member member);

    Member findByEmail(String email);

}
