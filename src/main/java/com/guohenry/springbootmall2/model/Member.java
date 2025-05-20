package com.guohenry.springbootmall2.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Member {

    private int id;

    @NotBlank(message = "姓名不得為空")
    private String name;

    @Email(message = "Email 格式錯誤")
    @NotBlank(message = "Email 不得為空")
    private String email;

    @NotBlank(message = "密碼不得為空")
    private String password;

    private String phone;
    private String role;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
