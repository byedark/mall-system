package com.xiatian.mallproduct.controller;

import com.xiatian.mallproduct.entity.Attr;
import com.xiatian.mallproduct.entity.AttrGroup;
import com.xiatian.mallproduct.service.AttrAttrgroupRelationService;
import com.xiatian.mallproduct.service.AttrGroupService;
import com.xiatian.mallproduct.service.AttrService;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.R;
import com.xiatian.mallproduct.vo.AttrGroupRelationVo;
import com.xiatian.mallproduct.vo.AttrGroupWithAttrsVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("attrgroup")
public class AttrGroupController {

    @Resource
    AttrGroupService attrGroupService;

    @Resource
    AttrService attrService;

    @Resource
    CategoryService categoryService;

    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;

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
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<Attr> entities = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data",entities);
    }
    @PostMapping(value = "/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {
        System.out.println(Arrays.toString(vos));
        attrService.deleteRelation(vos);
        return R.ok();
    }

    @GetMapping(value = "/{attrgroupId}/noattr/relation")
    public R attrNoattrRelation(@RequestParam Map<String, Object> params,
                                @PathVariable("attrgroupId") Long attrgroupId) {

        PageUtils page = attrService.getNoRelationAttr(params,attrgroupId);
        return R.ok().put("page",page);
    }

    @PostMapping(value = "/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        attrAttrgroupRelationService.saveBatchRelation(vos);
        return R.ok();
    }

    //获取分类下所有分组&关联属性
    @GetMapping(value = "/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
        //1、查出当前分类下的所有属性分组
        //2、查出每个属性分组下的所有属性
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data",vos);
    }
}
