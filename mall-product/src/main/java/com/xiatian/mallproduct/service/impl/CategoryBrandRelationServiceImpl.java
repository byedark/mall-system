package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.AttrGroup;
import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.entity.Category;
import com.xiatian.mallproduct.entity.CategoryBrandRelation;
import com.xiatian.mallproduct.mapper.BrandMapper;
import com.xiatian.mallproduct.mapper.CategoryMapper;
import com.xiatian.mallproduct.service.BrandService;
import com.xiatian.mallproduct.service.CategoryBrandRelationService;
import com.xiatian.mallproduct.mapper.CategoryBrandRelationMapper;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    CategoryMapper categoryMapper;

    @Resource
    BrandMapper brandMapper;

    @Resource
    CategoryBrandRelationMapper categoryBrandRelationMapper;

    //save接口
    //关联表格： 表格里有两个id，根据ID，更新时候补全表格
    @Override
    public void saveDetailInfo(CategoryBrandRelation categoryBrandRelation) {
        Category category = categoryMapper.selectById(categoryBrandRelation.getCatelogId());
        Brand brand = brandMapper.selectById(categoryBrandRelation.getBrandId());
        categoryBrandRelation.setBrandName(brand.getName());
        categoryBrandRelation.setCatelogName(category.getName());
        categoryBrandRelationMapper.insert(categoryBrandRelation);
    }

    //级联更新，适用于品牌更新，还可以写一个分类更新，不过分类更新用了基本方法
    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelation relationEntity = new CategoryBrandRelation();
        relationEntity.setBrandId(brandId);
        relationEntity.setBrandName(name);
        this.update(relationEntity,new UpdateWrapper<CategoryBrandRelation>().eq("brand_id",brandId));
    }

    //分类更新
    @Override
    public void updateCategory(Long catId, String name) {

        categoryBrandRelationMapper.updateCategory(catId,name);
    }
}




