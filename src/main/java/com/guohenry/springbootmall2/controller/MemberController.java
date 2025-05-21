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

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("member", new Member()); // 一定要傳
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute Member member,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        memberService.register(member);
        return "redirect:/login";
    }



    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        String redirectPath = (String) session.getAttribute("redirectAfterLogin");
        model.addAttribute("redirectPath", redirectPath);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam(required = false) String redirect,
                        HttpSession session,
                        Model model) {

        // 使用 service 層統一比對加密密碼
        Member member = memberService.login(email, password);

        if (member == null) {
            model.addAttribute("errorMessage", "帳號或密碼錯誤");
            return "login";
        }

        session.setAttribute("member", member);

        if (redirect != null && !redirect.isBlank()) {
            return "redirect:" + redirect;
        }

        String sessionRedirect = (String) session.getAttribute("redirectAfterLogin");
        if (sessionRedirect != null) {
            session.removeAttribute("redirectAfterLogin");
            return "redirect:" + sessionRedirect;
        }

        return "redirect:/member";
    }



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/member")
    public String memberPage() {
        return "member";
    }

    @PostMapping("/member/update-password")
    public String updatePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 Model model) {

        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "redirect:/login";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "新密碼與確認密碼不一致");
            return "member";
        }

        if (!new BCryptPasswordEncoder().matches(currentPassword, member.getPassword())) {
            model.addAttribute("errorMessage", "目前密碼錯誤");
            return "member";
        }

// 正確：加密後更新
        member.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        memberService.update(member);

        model.addAttribute("successMessage", "密碼已成功變更！");
        return "member";
    }

}

