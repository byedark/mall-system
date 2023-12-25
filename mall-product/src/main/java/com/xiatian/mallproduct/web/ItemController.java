package com.xiatian.mallproduct.web;

import com.xiatian.mallproduct.entity.SkuInfo;
import com.xiatian.mallproduct.service.SkuInfoService;
import com.xiatian.mallproduct.vo.SkuItemVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@Controller
public class ItemController {
    @Resource
    SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String getSkuInfoHtml(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException {
        //获取到信息，然后填入到thymleaf里面就可以了
        //System.out.println(skuId);
        SkuItemVo vo = skuInfoService.item(skuId);
        System.out.println(vo.toString());
        System.out.println("页面数据获取完毕");
        model.addAttribute("item", vo);
        return "newitem";
    }
}
