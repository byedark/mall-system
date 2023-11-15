package com.xiatian.mallproduct.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.entity.CategoryBrandRelation;
import com.xiatian.mallproduct.service.CategoryBrandRelationService;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.R;
import com.xiatian.mallproduct.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("categorybrandrelation")
public class BrandRelationController {
    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("/catelog/list")
    public Result<List<CategoryBrandRelation>> getList(@RequestParam Long brandId){
        LambdaQueryWrapper<CategoryBrandRelation> lambdaQueryWrapper = new LambdaQueryWrapper<CategoryBrandRelation>();
        lambdaQueryWrapper.eq(CategoryBrandRelation::getBrandId,brandId);
        List<CategoryBrandRelation> date = categoryBrandRelationService.list(lambdaQueryWrapper);
        return Result.ok(date);
    }
    @PostMapping("/save")
    public R saveDetail(@RequestBody CategoryBrandRelation categoryBrandRelation){
        categoryBrandRelationService.saveDetailInfo(categoryBrandRelation);
        return R.ok();
    }


    //关联分类不支持修改只支持重新设定，本来就是点一下的事，这个接口保留目前不会被调用
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelation categoryBrandRelation){
        categoryBrandRelationService.updateById(categoryBrandRelation);
        return R.ok();
    }
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
