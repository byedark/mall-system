package com.xiatian.mallproduct.web;

import com.xiatian.mallproduct.entity.Category;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.vo.Catelog2Vo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    CategoryService categoryService;

    @GetMapping(value = {"/","/index.html"})
    public String index(Model model){
        //首页渲染：获取一级分类数据
        List<Category> categoryEntities=categoryService.getLevel1();
        model.addAttribute("categories",categoryEntities);
        return "index";
    }

    //将返回的值以jason格式返回出去，而不是跳转页面，之前都是加了RestController，默认加了这个注解
    @ResponseBody
    @GetMapping("/index/catelog.json")
    public Map<String, List<Catelog2Vo>> getCategoryJson(){
        Map<String, List<Catelog2Vo>> ans =  categoryService.getCategory2();
        return ans;
    }

    @ResponseBody
    @GetMapping("/index/level1")
    public List<Category> getCategoryJsonTest(){
        return categoryService.getLevel1();
    }
}
