package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.utils.PageUtils;

import java.util.Map;

/**
* @author XT189
* @description 针对表【pms_brand(品牌)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/

public interface BrandService extends IService<Brand> {
    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(Brand brand);
}
