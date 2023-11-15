package com.xiatian.mallproduct.controller;


import com.xiatian.mallproduct.entity.Attr;
import com.xiatian.mallproduct.service.AttrService;
import com.xiatian.mallproduct.utils.R;
import com.xiatian.mallproduct.vo.AttrVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("attr")
public class AttrController {

    @Resource
    AttrService attrService;

    @GetMapping("/save")
    public R saveAttr(@RequestBody AttrVo attr){
        attrService.saveVo(attr);
        return R.ok();
    }
}
