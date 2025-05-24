package com.guohenry.springbootmall2.controllerApi;

import com.guohenry.springbootmall2.model.Member;
import com.guohenry.springbootmall2.service.MemberService;
import com.guohenry.springbootmall2.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "後台管理 API", description = "提供會員與訂單查詢功能")
public class AdminApiController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/members")
    @Operation(summary = "取得所有會員清單")
    public List<Member> getAllMembers() {
        return memberService.findAll();
    }

    @GetMapping("/orders/member/{memberId}")
    @Operation(summary = "查詢指定會員的訂單")
    public Object getMemberOrders(@PathVariable int memberId) {
        return orderService.getMemberOrders(memberId);
    }
}
