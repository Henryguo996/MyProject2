package com.guohenry.springbootmall2.config;

import org.springframework.ui.Model;
import com.guohenry.springbootmall2.model.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalModelAttributeAdvice {


    // @ModelAttribute 會把回傳資料加入到所有頁面的 model 中
    @ModelAttribute
    public void addMemberToModel(HttpSession session, Model model) {

        // 從 session 中取得目前登入的會員物件（如果有登入的話）
        Member member = (Member) session.getAttribute("member");

        // 如果會員不為 null（也就是使用者已登入）
        if (member != null) {
            // 將該會員物件加入到 model 中，讓所有 Thymeleaf 頁面都可以用 ${member.xxx} 取得會員資料
            model.addAttribute("member", member);
        }
    }
}

