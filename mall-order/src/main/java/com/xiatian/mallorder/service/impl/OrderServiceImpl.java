package com.xiatian.mallorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallorder.entity.Order;
import com.xiatian.mallorder.service.OrderService;
import com.xiatian.mallorder.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【oms_order(订单)】的数据库操作Service实现
* @createDate 2023-11-08 12:36:41
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




