package com.xiatian.mallorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallorder.entity.OrderItem;
import com.xiatian.mallorder.service.OrderItemService;
import com.xiatian.mallorder.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【oms_order_item(订单项信息)】的数据库操作Service实现
* @createDate 2023-11-08 12:36:42
*/
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
    implements OrderItemService{

}




