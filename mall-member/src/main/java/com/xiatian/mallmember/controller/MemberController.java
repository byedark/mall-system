package com.xiatian.mallmember.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.xiatian.mallmember.entity.Member;
import com.xiatian.mallmember.feign.CouponFeignService;
import com.xiatian.mallmember.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    CouponFeignService couponFeignService;

    @GetMapping("/coupons")
    public Result<HashMap<String,Object>> test(){
        System.out.println("远程过程调用Feign");
        Member member = new Member();
        member.setNickname("lalala");
        Result<HashMap<String,Object>> memberCoupons = couponFeignService.memberCoupons();
        HashMap<String,Object> msg = memberCoupons.getData();
        msg.put("member",member);
        return Result.ok(msg);
    }
}
