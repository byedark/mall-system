package com.xiatian.mallware.service;

import com.xiatian.mallware.entity.WareSku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author XT189
* @description 针对表【wms_ware_sku(商品库存)】的数据库操作Service
* @createDate 2023-11-08 12:32:54
*/
public interface WareSkuService extends IService<WareSku> {

    Object getSkuList(Map<String, Object> map);

    double addStock(Long skuId, Long wareId, Integer skuNum);
}
