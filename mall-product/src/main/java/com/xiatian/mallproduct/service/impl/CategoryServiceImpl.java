package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.Category;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author XT189
* @description 针对表【pms_category(商品三级分类)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public List<Category> listTree() {
        //直接进行全表搜索，不要递归查数据库，这会导致速度非常慢
        List<Category> listAll = categoryMapper.selectList(null);
        return listAll.stream().filter(listCategory -> listCategory.getParentCid()==0)
                .peek(listCategory -> listCategory
                        .setChildren(getChildrens(listCategory,listAll)))
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() ==null ? 0:menu1.getSort())- (menu2.getSort()==null?0:menu2.getSort());
                })
                .collect(Collectors.toList());
//        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        //一级列表限制查询个数为15条，建立索引进行二次优化
//        lambdaQueryWrapper.eq(Category::getParentCid,0).last("limit 15");
//        List<Category> list = categoryMapper.selectList(lambdaQueryWrapper);
//        list = list.stream().peek(listCategory -> listCategory
//                .setChildren(getChildrens(listCategory)))
//                .sorted(Comparator.comparingInt(Category::getSort))
//                .collect(Collectors.toList());
//        //形成父子关系，进行二级列表返回
    }

    public List<Category> getChildrens(Category parent,List<Category> listAll){
         return listAll.stream().filter(listCategory ->
                         Objects.equals(listCategory.getParentCid(), parent.getCatId()))
                .peek(listCategory -> listCategory
                        .setChildren(getChildrens(listCategory,listAll)))
                 .sorted((menu1, menu2) -> {
                     return (menu1.getSort() ==null ? 0:menu1.getSort())- (menu2.getSort()==null?0:menu2.getSort());
                 })
                .collect(Collectors.toList());
//        if(parent==null)
//            return null;
//        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Category::getParentCid,parent.getCatId());
//        List<Category> list = categoryMapper.selectList(lambdaQueryWrapper);
//        list = list.stream().peek(listCategory -> listCategory
//                        .setChildren(getChildrens(listCategory)))
//                .collect(Collectors.toList());
    }
}




