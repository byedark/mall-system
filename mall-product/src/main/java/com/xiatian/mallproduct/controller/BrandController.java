package com.xiatian.mallproduct.controller;

import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.service.BrandService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.R;
import com.xiatian.mallproduct.utils.Result;
import com.xiatian.mallproduct.valid.AddApplication;
import com.xiatian.mallproduct.valid.UpdateApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.HashAttributeSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println("查询品牌");
        PageUtils page = brandService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
        Brand brand = brandService.getById(brandId);
        return R.ok().put("brand",brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public Result<String> save(@Validated({AddApplication.class}) @RequestBody Brand brand){
        //给分组有分组的生效没有的失效，不给分组有分组全部失效
        brandService.save(brand);
        return Result.ok("OK");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public Result<String> update(@Validated({UpdateApplication.class}) @RequestBody Brand brand){
        brandService.updateDetail(brand);
        return Result.ok("OK");
    }
    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    //@RequiresPermissions("product:brand:update")
    public Result<String> updateStatus(@RequestBody Brand brand){
        brandService.updateById(brand);
        return Result.ok("OK");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public Result<String> delete(@RequestBody Long[] brandIds){
        brandService.removeByIds(Arrays.asList(brandIds));
        return Result.ok("OK");
    }
}
