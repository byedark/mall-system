package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.ProductAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.utils.PageUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @author XT189
* @description 针对表【pms_product_attr_value(spu属性值)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface ProductAttrValueService extends IService<ProductAttrValue> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValue> collect);

    List<ProductAttrValue> baseAttrListforspu(Long spuId);


    @Transactional(rollbackFor = Exception.class)
    void updateSpuAttr(Long spuId, List<ProductAttrValue> entities);
}
