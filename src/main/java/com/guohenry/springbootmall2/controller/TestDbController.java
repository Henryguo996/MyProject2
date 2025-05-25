package com.guohenry.springbootmall2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestDbController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test-db")
    public String testDB() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member", Integer.class);
        return "資料表總筆數：" + count;
    }
}

