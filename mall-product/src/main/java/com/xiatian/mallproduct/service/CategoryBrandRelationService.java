package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.CategoryBrandRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author XT189
* @description 针对表【pms_category_brand_relation(品牌分类关联)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface CategoryBrandRelationService extends IService<CategoryBrandRelation> {

    void updateBrand(Long brandId, String name);
}
