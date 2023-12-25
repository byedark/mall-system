package com.xiatian.authserver.controller;


import com.xiatian.authserver.component.SmsComponent;
import com.xiatian.authserver.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Resource
    SmsComponent smsComponent;
    //提供给别的服务进行调用
    @GetMapping
    public R sendCode(@RequestParam("phone") String phone,@RequestParam("code") String code) throws Exception {
        smsComponent.sendSmsCode(phone,code);
        return R.ok();
    }
}
