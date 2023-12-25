package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.SkuSaleAttrValue;
import com.xiatian.mallproduct.service.SkuSaleAttrValueService;
import com.xiatian.mallproduct.mapper.SkuSaleAttrValueMapper;
import com.xiatian.mallproduct.vo.ItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_sku_sale_attr_value(sku销售属性&值)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValue>
    implements SkuSaleAttrValueService{

    @Override
    public List<ItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId) {
        SkuSaleAttrValueMapper dao = this.baseMapper;
        return dao.getSaleAttrsBuSpuId(spuId);
    }
}




