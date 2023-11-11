package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.CategoryBrandRelation;
import com.xiatian.mallproduct.mapper.BrandMapper;
import com.xiatian.mallproduct.mapper.CategoryMapper;
import com.xiatian.mallproduct.service.BrandService;
import com.xiatian.mallproduct.service.CategoryBrandRelationService;
import com.xiatian.mallproduct.mapper.CategoryBrandRelationMapper;
import com.xiatian.mallproduct.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
* @author XT189
* @description 针对表【pms_category_brand_relation(品牌分类关联)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation>
    implements CategoryBrandRelationService{
    @Autowired
    BrandService brandService;



    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelation relationEntity = new CategoryBrandRelation();
        relationEntity.setBrandId(brandId);
        relationEntity.setBrandName(name);
        this.update(relationEntity,new UpdateWrapper<CategoryBrandRelation>().eq("brand_id",brandId));
    }

}




