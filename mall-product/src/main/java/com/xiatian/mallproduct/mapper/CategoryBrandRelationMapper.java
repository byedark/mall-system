package com.xiatian.mallproduct.mapper;

import com.xiatian.mallproduct.entity.CategoryBrandRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author XT189
* @description 针对表【pms_category_brand_relation(品牌分类关联)】的数据库操作Mapper
* @createDate 2023-11-07 15:02:23
* @Entity com.xiatian.mallproduct.entity.CategoryBrandRelation
*/
public interface CategoryBrandRelationMapper extends BaseMapper<CategoryBrandRelation> {

    void updateCategory(@Param("catId") Long catId, @Param("name")String name);
}




