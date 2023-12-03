package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
* @author XT189
* @description 针对表【pms_sku_info(sku信息)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface SkuInfoService extends IService<SkuInfo> {

    void saveSkuInfo(SkuInfo skuInfoEntity);

    PageUtils queryPageByCondtion(Map<String, Object> params);

    @Override
    default boolean save(SkuInfo entity) {
        return IService.super.save(entity);
    }
    List<SkuInfo> getSkusBySpuId(Long spuId);
}
