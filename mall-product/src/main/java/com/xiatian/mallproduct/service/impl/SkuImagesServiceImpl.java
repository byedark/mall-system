package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.SkuImages;
import com.xiatian.mallproduct.service.SkuImagesService;
import com.xiatian.mallproduct.mapper.SkuImagesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_sku_images(sku图片)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesMapper, SkuImages>
    implements SkuImagesService{

    @Override
    public List<SkuImages> getImagesBySkuId(Long skuId) {
        SkuImagesMapper dao = this.baseMapper;
        return dao.selectList(new QueryWrapper<SkuImages>().eq("sku_id", skuId));
    }
}




