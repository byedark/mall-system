package com.xiatian.mallproduct.controller;

import com.xiatian.mallproduct.entity.SpuInfo;
import com.xiatian.mallproduct.service.SpuInfoService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.R;
import com.xiatian.mallproduct.utils.Result;
import com.xiatian.mallproduct.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("spuinfo")
public class SpuInfoController {

    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 根据skuId查询spu的信息
     * @param skuId
     * @return
     */
    @GetMapping(value = "/skuId/{skuId}")
    public Result<SpuInfo> getSpuInfoBySkuId(@PathVariable("skuId") Long skuId) {
        SpuInfo spuInfo = spuInfoService.getSpuInfoBySkuId(skuId);
        return Result.ok(spuInfo);
    }

    //商品上架
    ///product/spuinfo/{spuId}/up
    @PostMapping(value = "/{spuId}/up")
    public R spuUp(@PathVariable("spuId") Long spuId) {
        spuInfoService.up(spuId);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPageByCondtion(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("id") Long id){
        SpuInfo spuInfo = spuInfoService.getById(id);
        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:spuinfo:save")
    public R save(@RequestBody SpuSaveVo vo){
        //spuInfoService.save(spuInfo);
        spuInfoService.savesupInfo(vo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:spuinfo:update")
    public R update(@RequestBody SpuInfo spuInfo){
        spuInfoService.updateById(spuInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:spuinfo:delete")
    public R delete(@RequestBody Long[] ids){
        spuInfoService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
