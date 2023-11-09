package com.xiatian.mallproduct.controller;

import com.xiatian.mallproduct.entity.Category;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("product")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list/tree")
    public Result<List<Category>> productList(){
        List<Category> list = categoryService.listTree();

        return Result.ok(list);
    }
}
