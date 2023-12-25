package com.xiatian.mallproduct.mapper;

import com.xiatian.mallproduct.entity.SkuSaleAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiatian.mallproduct.vo.ItemSaleAttrVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_sku_sale_attr_value(sku销售属性&值)】的数据库操作Mapper
* @createDate 2023-11-07 15:02:23
* @Entity com.xiatian.mallproduct.entity.SkuSaleAttrValue
*/
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {

    List<ItemSaleAttrVo> getSaleAttrsBuSpuId(@Param("spuId")Long spuId);
}




