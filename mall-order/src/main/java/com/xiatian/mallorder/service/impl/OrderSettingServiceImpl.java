package com.xiatian.mallorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallorder.entity.OrderSetting;
import com.xiatian.mallorder.service.OrderSettingService;
import com.xiatian.mallorder.mapper.OrderSettingMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【oms_order_setting(订单配置信息)】的数据库操作Service实现
* @createDate 2023-11-08 12:36:42
*/
@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting>
    implements OrderSettingService{

}




