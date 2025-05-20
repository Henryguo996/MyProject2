package com.guohenry.springbootmall2.controller;


import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member());
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



//    @GetMapping("/register")
//    public String showRegisterForm(Member member) {
//
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String processRegister(@Valid @ModelAttribute Member member,
//                                  BindingResult result) {
//        if (result.hasErrors()) {
//            return "register";
//        }
//        memberService.register(member);
//        return "redirect:/login";
//    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session) {
        Member member = memberService.login(email, password);
        if (member != null) {
            session.setAttribute("member", member);
            return "redirect:/member";
        } else {
            return "redirect:/login?error";
        }
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
}

