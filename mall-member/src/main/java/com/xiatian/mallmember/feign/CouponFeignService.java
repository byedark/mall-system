package com.xiatian.mallmember.feign;

import com.baomidou.mybatisplus.extension.api.R;
import com.xiatian.mallmember.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

//写明了调用哪个服务,写的是feign中的服务
@FeignClient("coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/list")
    public Result<HashMap<String, Object>> memberCoupons();
}
