package com.xiatian.mallproduct.controller;


import com.xiatian.mallproduct.entity.Attr;
import com.xiatian.mallproduct.entity.ProductAttrValue;
import com.xiatian.mallproduct.service.AttrService;
import com.xiatian.mallproduct.service.ProductAttrValueService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.R;
import com.xiatian.mallproduct.utils.Result;
import com.xiatian.mallproduct.vo.AttrRespVo;
import com.xiatian.mallproduct.vo.AttrVo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("attr")
public class AttrController {

    @Resource
    AttrService attrService;

    @Resource
    ProductAttrValueService productAttrValueService;
    @PostMapping("/save")
    public R saveAttr(@RequestBody AttrVo attr){
        attrService.saveVo(attr);
        return R.ok();
    }
    //分为了销售属性和普通属性,base和sale，两者是通过一个id进行区分的
    @GetMapping("/{attr}/list/{catelogId}")
    public R listGetAttr(@PathVariable("attr") String attr,
                         @PathVariable("catelogId") Long catelogId,
                         @RequestParam Map<String,Object> mp){
        PageUtils page = attrService.queryBaseAttrPage(mp,catelogId,attr);
        return R.ok().put("page",page);
    }

    /**
     *  获取spu规格
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){
        List<ProductAttrValue> entities = productAttrValueService.baseAttrListforspu(spuId);
        return R.ok().put("data",entities);
    }

    @PostMapping("/update/{spuId}")
    //@RequiresPermissions("product:attr:update")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValue> entities){
        productAttrValueService.updateSpuAttr(spuId,entities);
        return R.ok();
    }


    @GetMapping("/info/{attrId}")
    public R attrInfo(@PathVariable("attrId") Long attrId){
        AttrRespVo attrRespVo = attrService.getAttrInfo(attrId);
        return R.ok().put("attr",attrRespVo);
    }

    @PostMapping("/update")
    public Result<String> updateAttr(@RequestBody AttrVo attrVo){
        attrService.updateAttrById(attrVo);
        return Result.ok("ok");
    }


}
