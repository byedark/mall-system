package com.xiatian.mallmember.controller;

import com.xiatian.mallmember.service.MemberLevelService;
import com.xiatian.mallmember.service.MemberService;
import com.xiatian.mallmember.utils.PageUtils;
import com.xiatian.mallmember.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("memberlevel")
public class MemberLevelController {
    @Resource
    MemberLevelService memberLevelService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String,Object> params){
        //会员等级为什么要分页，我不能理解
        PageUtils pageUtils = memberLevelService.findLevel(params);
        return R.ok().put("page",pageUtils);
    }
}
