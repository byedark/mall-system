package com.xiatian.authserver.feign;

import com.xiatian.authserver.utils.R;
import com.xiatian.authserver.vo.UserLoginVo;
import com.xiatian.authserver.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("member")
public interface MemberFeignService {
    @RequestMapping("/member/member/register")
    R register(@RequestBody UserRegistVo vo);

    @RequestMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);
}
