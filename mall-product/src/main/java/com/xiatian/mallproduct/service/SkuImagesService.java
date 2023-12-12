package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.SkuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_sku_images(sku图片)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface SkuImagesService extends IService<SkuImages> {

    List<SkuImages> getImagesBySkuId(Long skuId);
}
