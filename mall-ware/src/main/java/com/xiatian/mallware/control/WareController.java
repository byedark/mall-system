package com.xiatian.mallware.control;


import com.xiatian.mallware.service.WareInfoService;
import com.xiatian.mallware.utils.PageUtils;
import com.xiatian.mallware.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("wareinfo")
public class WareController {

    @Resource
    WareInfoService wareInfoService;

    @GetMapping("/list")
    public R getWares(@RequestParam Map<String,Object> map){
        return R.ok().put("page",wareInfoService.getWare(map));
    }

}
