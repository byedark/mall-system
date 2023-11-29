package com.xiatian.mallware.control;

import com.xiatian.mallware.service.WareInfoService;
import com.xiatian.mallware.service.WareSkuService;
import com.xiatian.mallware.service.impl.WareInfoServiceImpl;
import com.xiatian.mallware.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
}
