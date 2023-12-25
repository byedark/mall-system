package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.SpuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_spu_images(spu图片)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface SpuImagesService extends IService<SpuImages> {

    void saveImages(Long id, List<String> images);
}
