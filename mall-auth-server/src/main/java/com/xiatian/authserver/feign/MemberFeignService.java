package com.xiatian.authserver.feign;

import com.xiatian.authserver.utils.R;
import com.xiatian.authserver.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("member")
public interface MemberFeignService {
    @RequestMapping("/member/member/register")
    R register(UserRegistVo vo);
}
