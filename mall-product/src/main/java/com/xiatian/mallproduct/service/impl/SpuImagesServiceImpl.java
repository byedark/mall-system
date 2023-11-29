package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.SpuImages;
import com.xiatian.mallproduct.service.SpuImagesService;
import com.xiatian.mallproduct.mapper.SpuImagesMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author XT189
* @description 针对表【pms_spu_images(spu图片)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImages>
    implements SpuImagesService{
    @Override
    public void saveImages(Long id, List<String> images) {
        if (images == null || images.size() == 0) {
            log.warn("图片保存");
        } else {
            List<SpuImages> collect = images.stream().map(img -> {
                SpuImages spuImagesEntity = new SpuImages();
                spuImagesEntity.setSpuId(id);
                spuImagesEntity.setImgUrl(img);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }
}




