package com.guohenry.springbootmall2.repository.Impl;

import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM member ORDER BY id";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Member.class));
    }


    @Override
    public void save(Member member) {
        String sql = "INSERT INTO member(name, email, password, phone, role) " +
                "VALUES(:name, :email, :password, :phone, :role)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = :email";
        try {
            return namedParameterJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource("email", email),
                    new BeanPropertyRowMapper<>(Member.class));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Member findById(int id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getInt("id"));
            member.setEmail(rs.getString("email"));
            member.setName(rs.getString("name"));
            member.setPassword(rs.getString("password"));
            return member;
        });
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE member SET password = ? WHERE id = ?";
        jdbc.update(sql, member.getPassword(), member.getId());
    }
}
