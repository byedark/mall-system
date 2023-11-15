package com.xiatian.mallproduct.controller;

import com.xiatian.mallproduct.entity.Attr;
import com.xiatian.mallproduct.entity.AttrGroup;
import com.xiatian.mallproduct.service.AttrGroupService;
import com.xiatian.mallproduct.service.AttrService;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("attrgroup")
public class AttrGroupController {

    @Resource
    AttrGroupService attrGroupService;


    @Resource
    CategoryService categoryService;


    @GetMapping("/list/{catelogId}")
    public R attrFetch(@RequestParam HashMap<String,Object> hashMap, @PathVariable Long catelogId){
        PageUtils pageUtils = attrGroupService.queryPage(hashMap,catelogId);
        return R.ok().put("page",pageUtils);
    }

    @GetMapping("/info/{attrGroupId}")
    public R attrGroupGetById(@PathVariable Long attrGroupId){
        AttrGroup attrGroup = attrGroupService.getById(attrGroupId);
        //得到三级分类
        Long attr = attrGroup.getCatelogId();
        Long[] path = categoryService.getByAttrGroupId(attr);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup",attrGroup);
    }

    @RequestMapping("/save")
    public R saveAttr(@RequestBody AttrGroup attrGroup){
        attrGroupService.save(attrGroup);
        return R.ok();
    }

    @RequestMapping("/update")
    public R updateAttr(@RequestBody AttrGroup attrGroup){
        attrGroupService.save(attrGroup);
        return R.ok();
    }
}
