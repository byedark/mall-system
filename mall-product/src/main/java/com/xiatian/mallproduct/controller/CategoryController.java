package com.xiatian.mallproduct.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.xiatian.mallproduct.entity.Category;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list/tree")
    public Result<List<Category>> productList(){
        System.out.println("分类查询");
        List<Category> list = categoryService.listTree();
        return Result.ok(list);
    }
    @PostMapping("/delete")
    public Result<String> deleteCategory(@RequestBody Long[] categoryId){
        System.out.println("删除分类");
        return categoryService.deleteCategory(Arrays.asList(categoryId));
    }
    @PostMapping("/save")
    public Result<String> Category(@RequestBody Category category){
        System.out.println("增加分类");
        categoryService.save(category);
        return Result.ok("OK");
    }
    @GetMapping("/info/{catId}")
    public Result<HashMap<String,Category>> Category(@PathVariable Long catId){
        System.out.println("查询增加的分类");
        //注意此处的PathVariable的用法，PathParam是传统的name=1这种，Variable是restful风格的
        Category category = categoryService.getById(catId);
        HashMap<String,Category> hashMap = new HashMap<>();
        hashMap.put("category",category);
        return Result.ok(hashMap);
    }
    @PostMapping("/update/sort")
    public Result<String> Category(@RequestBody Category[] categories){
        System.out.println("拖拽排序");
        categoryService.updateBatchById(Arrays.asList(categories));
        return Result.ok("OK");
    }
}
