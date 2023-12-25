package com.xiatian.mallcoupon.controller;

import com.xiatian.mallcoupon.entity.SpuBounds;
import com.xiatian.mallcoupon.service.SpuBoundsService;
import com.xiatian.mallcoupon.utils.PageUtils;
import com.xiatian.mallcoupon.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;


@RestController
@RefreshScope
@RequestMapping("spubounds")
public class SpuBoundsController {
    @Autowired
    private SpuBoundsService spuBoundsService;

    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:spubounds:info")
    public R info(@PathVariable("id") Long id){
        SpuBounds spuBounds = spuBoundsService.getById(id);
        return R.ok().put("spuBounds", spuBounds);
    }

    @RequestMapping("/save")
    public R save(@RequestBody SpuBounds spuBounds){
        System.out.println("远程调用了spubounds/save");
        spuBoundsService.save(spuBounds);
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:spubounds:delete")
    public R delete(@RequestBody Long[] ids){
        spuBoundsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
