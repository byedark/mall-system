package com.xiatian.mallproduct.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.service.BrandService;
import com.xiatian.mallproduct.mapper.BrandMapper;
import com.xiatian.mallproduct.service.CategoryBrandRelationService;
import com.xiatian.mallproduct.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
* @author XT189
* @description 针对表【pms_brand(品牌)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand>
    implements BrandService{

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Resource
    BrandMapper brandMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //1、获取key
        String key = (String) params.get("key");
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            queryWrapper.eq("brand_id",key).or().like("name",key);
        }
        Page<Brand> pagex = new Page<>();
        long pageNum = Long.parseLong((String) params.get("page"));
        pagex.setCurrent(pageNum);
        long pageCount = Long.parseLong((String) params.get("limit"));
        pagex.setSize(pageCount);
        //传入一个page对象和一个wapper对象
        IPage<Brand> page = brandMapper.selectPage(pagex,queryWrapper);
        return new PageUtils(page);
    }

    //分类表中也有一个这个函数
    @Transactional
    @Override
    public void updateDetail(Brand brand) {
        //保证冗余字段的数据一致
        this.updateById(brand);
        if(!StringUtils.isEmpty(brand.getName())){
            //同步更新其他关联表中的数据
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
        }
    }
}




