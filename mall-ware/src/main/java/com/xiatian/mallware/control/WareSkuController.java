package com.xiatian.mallware.control;

import com.xiatian.mallware.service.WareInfoService;
import com.xiatian.mallware.service.WareSkuService;
import com.xiatian.mallware.service.impl.WareInfoServiceImpl;
import com.xiatian.mallware.utils.R;
import com.xiatian.mallware.vo.SkuHasStockVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("waresku")
public class WareSkuController {
    @Resource
    WareSkuService wareSkuService;

    @GetMapping("/list")
    public R getWareSku(@RequestParam Map<String,Object> map){
        return R.ok().put("page",wareSkuService.getSkuList(map));
    }
    @PostMapping("hasstock")
    public R getSkuHasStock(@RequestBody List<Long> skuIds){
        List<SkuHasStockVo> vos = wareSkuService.getSkuHasStock(skuIds);
        return R.ok().put("data",vos);
    }
}
