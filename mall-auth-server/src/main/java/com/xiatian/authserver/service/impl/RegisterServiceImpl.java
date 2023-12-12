package com.xiatian.authserver.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.xiatian.authserver.constant.AuthServerConstant;
import com.xiatian.authserver.service.RegisterService;
import com.xiatian.authserver.vo.UserRegistVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public void checkInfo(UserRegistVo vo) {
        return ;
    }
}
