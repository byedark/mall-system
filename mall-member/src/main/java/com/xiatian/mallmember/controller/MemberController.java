package com.xiatian.mallmember.controller;

import com.xiatian.mallmember.entity.Member;
import com.xiatian.mallmember.exception.ExistException;
import com.xiatian.mallmember.feign.CouponFeignService;
import com.xiatian.mallmember.service.MemberService;
import com.xiatian.mallmember.utils.BizCodeEnum;
import com.xiatian.mallmember.utils.R;
import com.xiatian.mallmember.utils.Result;
import com.xiatian.mallmember.vo.UserRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    MemberService memberService;
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
    @PostMapping("/register")
    public R register(@RequestBody UserRegistVo vo){
        try{
            memberService.register(vo);
        }catch(ExistException e){
            //账号已经存在
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(),BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }
}
