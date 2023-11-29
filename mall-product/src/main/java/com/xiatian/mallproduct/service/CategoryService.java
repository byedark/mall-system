package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.utils.Result;
import com.xiatian.mallproduct.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

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

    List<Long> findParentCategory(Long curCate, List<Long> result);

    List<Category> getLevel1();

    Map<String, List<Catelog2Vo>> getCategory2();
}
