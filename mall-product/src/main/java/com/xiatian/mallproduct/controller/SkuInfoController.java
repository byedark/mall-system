package com.xiatian.mallproduct.controller;


import com.xiatian.mallproduct.service.SkuInfoService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("skuinfo")
public class SkuInfoController {
    @Resource
    SkuInfoService skuInfoService;

    @RequestMapping("/list")
    //@RequiresPermissions("product:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuInfoService.queryPageByCondtion(params);
        return R.ok().put("page", page);
    }

}
