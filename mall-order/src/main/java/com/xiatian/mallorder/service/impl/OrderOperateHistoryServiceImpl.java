package com.xiatian.mallorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallorder.entity.OrderOperateHistory;
import com.xiatian.mallorder.service.OrderOperateHistoryService;
import com.xiatian.mallorder.mapper.OrderOperateHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【oms_order_operate_history(订单操作历史记录)】的数据库操作Service实现
* @createDate 2023-11-08 12:36:42
*/
@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory>
    implements OrderOperateHistoryService{

}




