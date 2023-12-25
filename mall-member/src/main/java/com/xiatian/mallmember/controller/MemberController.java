package com.xiatian.mallmember.controller;

import com.xiatian.mallmember.entity.Member;
import com.xiatian.mallmember.exception.ExistException;
import com.xiatian.mallmember.feign.CouponFeignService;
import com.xiatian.mallmember.service.MemberService;
import com.xiatian.mallmember.utils.BizCodeEnum;
import com.xiatian.mallmember.utils.R;
import com.xiatian.mallmember.utils.Result;
import com.xiatian.mallmember.vo.SocialUser;
import com.xiatian.mallmember.vo.UserLoginVo;
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
    @PostMapping("/login")
    public R login(@RequestBody UserLoginVo vo){
        Member entity = memberService.login(vo);
        if(entity!=null){
            return R.ok();
        }else{
            return R.error(BizCodeEnum.LOGINACTT_PASSWORD_ERROR.getCode(),BizCodeEnum.LOGINACTT_PASSWORD_ERROR.getMsg());
        }
    }
    @PostMapping("/oauth2/login")
    public R oauthLogin(@RequestBody SocialUser socialUser){
        Member member = memberService.authLogin(socialUser);
        if(member != null){
            return R.ok().put("data",member);
        }else {
            return R.error(BizCodeEnum.SOCIALUSER_LOGIN_ERROR.getCode(), BizCodeEnum.SOCIALUSER_LOGIN_ERROR.getMsg());
        }
    }
}
