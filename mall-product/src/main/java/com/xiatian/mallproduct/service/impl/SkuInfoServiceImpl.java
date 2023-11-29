package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.SkuInfo;
import com.xiatian.mallproduct.service.SkuInfoService;
import com.xiatian.mallproduct.mapper.SkuInfoMapper;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
* @author XT189
* @description 针对表【pms_sku_info(sku信息)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
    implements SkuInfoService{

    @Override
    public void saveSkuInfo(SkuInfo skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondtion(Map<String, Object> params) {
        LambdaQueryWrapper<SkuInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            lambdaQueryWrapper.and((w) -> {
                w.eq(SkuInfo::getSkuId,key).or().like(SkuInfo::getSkuName,key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.hasText(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            lambdaQueryWrapper.eq(SkuInfo::getCatalogId,catelogId);
        }

        String brandId = (String) params.get("brandId");
        if (StringUtils.hasText(brandId) && !"0".equalsIgnoreCase(brandId)) {
            lambdaQueryWrapper.eq(SkuInfo::getBrandId,brandId);
        }

        String min = (String) params.get("min");
        if (StringUtils.hasText(min)) {
            lambdaQueryWrapper.ge(SkuInfo::getPrice,min);
        }

        String max = (String) params.get("max");
        if (StringUtils.hasText(max)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(max);
                if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
                    lambdaQueryWrapper.le(SkuInfo::getPrice,max);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IPage<SkuInfo> page = this.page(new Query<SkuInfo>().getPage(params));
        return new PageUtils(page);
    }

}




