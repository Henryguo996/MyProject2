package com.guohenry.springbootmall2.config;

import org.springframework.ui.Model;
import com.guohenry.springbootmall2.model.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @ModelAttribute
    public void addMemberToModel(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            model.addAttribute("member", member);
        }
    }
}
