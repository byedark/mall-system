package com.xiatian.mallproduct.vo;

import com.xiatian.mallproduct.entity.SkuImages;
import com.xiatian.mallproduct.entity.SkuInfo;
import com.xiatian.mallproduct.entity.SpuInfoDesc;
import lombok.Data;

import java.util.List;


@Data
public class SkuItemVo {
    /**
     * 基本信息
     */
    SkuInfo info;

    boolean hasStock = true;

    /**
     * 图片信息
     */
    List<SkuImages> images;

    /**
     * 销售属性组合
     */
    List<ItemSaleAttrVo> saleAttr;

    /**
     * 介绍
     */
    SpuInfoDesc desc;

    /**
     * 参数规格信息
     */
    List<SpuItemAttrGroup> groupAttrs;

    /**
     * 秒杀信息
     */
    SeckillInfoVo seckillInfoVo;
}

