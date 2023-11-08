package com.xiatian.mallcoupon.controller;

import com.xiatian.mallcoupon.entity.Coupon;
import com.xiatian.mallcoupon.service.CouponService;
import com.xiatian.mallcoupon.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("coupon")
public class CouponController {
    @Autowired
    CouponService couponService;

    @GetMapping("/list")
    public Result<HashMap<String, List<?>>> memberCoupons(){
        System.out.println("feign调试进入");
        HashMap<String, List<?>> hashMap = new HashMap<String,List<?>>();
        Coupon coupon1 = new Coupon();
        coupon1.setCouponName("discount 10%");
        Coupon coupon2 = new Coupon();
        coupon2.setCouponName("discount 20%");
        hashMap.put("coupons", Arrays.asList(coupon1,coupon2));
        return Result.ok(hashMap);
    }
}
