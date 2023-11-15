package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.utils.Result;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_category(商品三级分类)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface CategoryService extends IService<Category> {

    List<Category> listTree();

    Result<String> deleteCategory(List<Long> categoryId);

    Long[] getByAttrGroupId(Long attr);

    void updateDetail(Category category);
}
