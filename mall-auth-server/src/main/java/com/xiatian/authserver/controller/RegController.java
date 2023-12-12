package com.xiatian.authserver.controller;

import com.xiatian.authserver.component.SmsComponent;
import com.xiatian.authserver.constant.AuthServerConstant;
import com.xiatian.authserver.utils.BizCodeEnume;
import com.xiatian.authserver.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class RegController {

    @Resource
    private SmsComponent smsComponent;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) throws Exception {
        //防刷，页面刷新也要不能用户再次发送，判断redis里的数据，是否小于9分钟
        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone);
        if(redisCode!=null) {
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60000) {
                return R.error(10001, "请不要反复提交验证码申请");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        //还可以使用UUID来生成验证码来获得信息
        //生成0到9之间的随机数字并添加到StringBuilder中
        //String类都是基于StringBuilder类的
        for (int i = 0; i < 4; i++)
            stringBuilder.append(random.nextInt(10));
        //存一份这个code到redis里面
        //把phone和code进行一次绑定，然后设置一个10分钟的过期时间,由于redis后一个覆盖了前面一个
        String code = stringBuilder.substring(0,4);
        String codeRedis = code+"_"+System.currentTimeMillis();
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone,codeRedis,10, TimeUnit.MINUTES);
        System.out.println(code);
        smsComponent.sendSmsCode(phone,code);
        return R.ok();
    }
}
