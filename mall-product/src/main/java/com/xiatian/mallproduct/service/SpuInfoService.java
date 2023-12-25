package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.SpuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.vo.SpuSaveVo;

import java.util.Map;

/**
* @author XT189
* @description 针对表【pms_spu_info(spu信息)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface SpuInfoService extends IService<SpuInfo> {
    
    PageUtils queryPageByCondtion(Map<String,Object> params);

    void up(Long spuId);

    void saveBaseSpuInfo(SpuInfo spuInfoEntity);

    void savesupInfo(SpuSaveVo vo);

    SpuInfo getSpuInfoBySkuId(Long skuId);
}
