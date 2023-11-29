package com.xiatian.mallware.mapper;

import com.xiatian.mallware.entity.WareSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author XT189
* @description 针对表【wms_ware_sku(商品库存)】的数据库操作Mapper
* @createDate 2023-11-08 12:32:54
* @Entity com.xiatian.mallware.entity.WareSku
*/
public interface WareSkuMapper extends BaseMapper<WareSku> {

    void addStock(Long skuId, Long wareId, Integer skuNum);
}




