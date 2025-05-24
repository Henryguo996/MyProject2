package com.guohenry.springbootmall2.controllerApi;

import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@Tag(name = "會員 API", description = "會員註冊、登入、查詢相關功能")
public class MemberApiController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    @Operation(summary = "取得所有會員")
    public List<Member> getAllMembers() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根據 ID 取得會員")
    public Member getMemberById(@PathVariable int id) {
        return memberService.findById(id);
    }

    @PostMapping("/register")
    @Operation(summary = "會員註冊")
    public void register(@Valid @RequestBody Member member) {
        memberService.register(member);
    }

    @PostMapping("/login")
    @Operation(summary = "會員登入")
    public Member login(@RequestParam String email, @RequestParam String password) {
        return memberService.login(email, password);
    }
}
