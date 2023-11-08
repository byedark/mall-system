package com.xiatian.mallorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallorder.entity.PaymentInfo;
import com.xiatian.mallorder.service.PaymentInfoService;
import com.xiatian.mallorder.mapper.PaymentInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【oms_payment_info(支付信息表)】的数据库操作Service实现
* @createDate 2023-11-08 12:36:42
*/
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo>
    implements PaymentInfoService{

}




