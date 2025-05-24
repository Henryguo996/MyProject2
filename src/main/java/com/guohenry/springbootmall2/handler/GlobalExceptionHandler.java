package com.guohenry.springbootmall2.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 全域控制器攔截所有 Controller 的錯誤，統一處理
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {

        // 把錯誤訊息放入 model，傳給錯誤頁面顯示
        model.addAttribute("message", ex.getMessage());
        // 導向錯誤頁面
        return "error/500";
    }


    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointer(Exception ex, Model model) {
        // 將錯誤訊息加到 model 中，傳到錯誤頁面顯示
        model.addAttribute("message", "系統錯誤：資料為空");

        return "error/500";
    }
}

