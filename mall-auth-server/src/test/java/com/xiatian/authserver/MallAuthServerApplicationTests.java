package com.xiatian.authserver;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.*;
import com.xiatian.authserver.component.SmsComponent;
import com.xiatian.authserver.component.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MallAuthServerApplicationTests {

    @Resource
    SmsComponent smsComponent;
    @Resource
    TestConfig testConfig;
    @Test
    void smsTest() throws Exception {
        smsComponent.sendSmsCode("18914915375","7748");
    }
    @Test
    void xTest() throws Exception {
        System.out.println(testConfig.getX());
    }

}
