 package com.guohenry.springbootmall2.controller;

import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class MemberController {


    @Autowired
    private MemberService memberService;

    // 建立一個加密器物件，用來加密使用者密碼（登入與註冊）
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 顯示註冊頁面
    @GetMapping("/register")
    public String registerPage(Model model) {
        // 頁面需要一個空的 member 物件做為表單綁定
        model.addAttribute("member", new Member());

        return "users/register";
    }

    //  處理註冊表單提交
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute Member member, // 自動綁定表單欄位 + 驗證註解
                                  BindingResult result) {
        // 若驗證失敗，回到註冊頁
        if (result.hasErrors()) {
            return "users/register";
        }

        // 呼叫 service 註冊會員（內部應包含密碼加密）
        memberService.register(member);


        return "redirect:/users/login";
    }

    //  顯示登入頁
    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        // 若使用者是從登入保護頁面跳轉，先記住原始目標網址
        String redirectPath = (String) session.getAttribute("redirectAfterLogin");

        // 傳入 model 給登入頁判斷是否導回原路徑
        model.addAttribute("redirectPath", redirectPath);

        return "users/login";
    }

    //  處理登入表單送出
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam(required = false) String redirect, // 用來接收隱藏欄位的回跳網址
                        HttpSession session,
                        Model model) {

        // 驗證帳號密碼（加密比對）
        Member member = memberService.login(email, password);
        System.out.println("登入成功：member = " + member.getName());

        // 登入失敗（帳號密碼錯誤）
        if (member == null) {
            model.addAttribute("errorMessage", "帳號或密碼錯誤");
            return "users/login";
        }

        // 登入成功 將會員資料存入
        session.setAttribute("member", member);

        // 若是管理員，轉去後台
        if ("ADMIN".equalsIgnoreCase(member.getRole())) {
            return "redirect:/admin/dashboard";
        }


        // 若有指定 redirect（從前頁跳轉），優先回去
        if (redirect != null && !redirect.isBlank()) {
            return "redirect:" + redirect;
        }

        // 若 session 中記錄有 redirectAfterLogin，則使用它並清除
        String sessionRedirect = (String) session.getAttribute("redirectAfterLogin");
        if (sessionRedirect != null) {
            session.removeAttribute("redirectAfterLogin");
            return "redirect:" + sessionRedirect;
        }


        return "redirect:/";
    }

    // 登出功能
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除所有 session 資料（等同登出）
        session.invalidate();


        // 登出後加上 URL 參數 ?logout=true
        return "redirect:/?logout=true";

    }

    //  顯示會員中心頁面
    @GetMapping("/member")
    public String memberPage(HttpSession session, Model model) {
        // 從 session 取得會員資料
        Member member = (Member) session.getAttribute("member");

        // 未登入者導向登入頁
        if (member == null) {
            return "redirect:/users/login";
        }

        //查檢除錯用
        //System.out.println("登入成功：member = " + member.getName());

        // 登入者的資訊塞進 model 傳給前端顯示
        model.addAttribute("member", member);
        return "users/member"; // 對應到 templates/users/member.html
    }

    // 處理會員修改密碼
    @PostMapping("/member/update-password")
    public String updatePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 Model model) {

        // 從 session 取得目前登入的會員
        Member member = (Member) session.getAttribute("member");

        // 未登入者導回登入頁
        if (member == null) {
            return "redirect:/users/login";
        }

        // 新密碼與確認密碼不一致，顯示錯誤訊息
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "新密碼與確認密碼不一致");
            return "users/member";
        }

        // 驗證目前密碼是否正確（與資料庫比對）
        if (!new BCryptPasswordEncoder().matches(currentPassword, member.getPassword())) {
            model.addAttribute("errorMessage", "目前密碼錯誤");
            return "users/member";
        }

        // 若驗證通過 → 將新密碼加密後更新
        member.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        memberService.update(member); // 呼叫 service 寫回資料庫

        model.addAttribute("successMessage", "密碼已成功變更！");
        return "users/member";
    }


}