package com.guohenry.springbootmall2.repository;

import com.guohenry.springbootmall2.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void save(Member member) {
        String sql = "INSERT INTO member(name, email, password, phone, role) " +
                "VALUES(:name, :email, :password, :phone, :role)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, param);
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = :email";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource("email", email),
                    new BeanPropertyRowMapper<>(Member.class));
        } catch (Exception e) {
            return null;
        }
    }
}
