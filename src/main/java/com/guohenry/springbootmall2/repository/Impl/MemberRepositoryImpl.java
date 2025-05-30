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

    // NamedParameterJdbcTemplate：支援命名參數（:name, :email）
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // JdbcTemplate：支援傳統問號佔位查詢
    @Autowired
    private JdbcTemplate jdbc;

    //  查詢全部會員資料（排序依 id 遞增）
    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM member ORDER BY id";
        // 直接用 BeanPropertyRowMapper 將結果轉成 List<Member>
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Member.class));
    }

    // 新增一筆會員資料（用於註冊）
    @Override
    public void save(Member member) {
        String sql = "INSERT INTO member(name, email, password, phone, role) " +
                "VALUES(:name, :email, :password, :phone, :role)";

        // 透過 BeanPropertySqlParameterSource 自動將 member 欄位對應到命名參數
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);

        namedParameterJdbcTemplate.update(sql, param);
    }

    // 根據 Email 查詢會員（用於登入）
    @Override
    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = :email";
        try {
            // 用 Map 傳入 :email 參數，查詢結果轉成 Member 物件
            return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("email", email),
                    new BeanPropertyRowMapper<>(Member.class));
        } catch (Exception e) {
            // 找不到則回傳 null（例如帳號不存在）
            return null;
        }
    }

    // 根據 ID 查詢會員（後台、修改密碼等）
    @Override
    public Member findById(int id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbc.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> { // lambda 表達式轉換 ResultSet → Member
                    Member member = new Member();
                    member.setId(rs.getInt("id"));
                    member.setEmail(rs.getString("email"));
                    member.setName(rs.getString("name"));
                    member.setPassword(rs.getString("password"));
                    return member;
                }
        );
    }

    // 更新會員密碼（用於會員中心修改密碼）
    @Override
    public void update(Member member) {
        String sql = "UPDATE member SET password = ? WHERE id = ?";
        jdbc.update(sql, member.getPassword(), member.getId());
    }
}
