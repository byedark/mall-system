package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.Category;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【pms_category(商品三级分类)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




