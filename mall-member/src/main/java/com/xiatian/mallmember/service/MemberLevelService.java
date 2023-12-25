package com.xiatian.mallmember.service;

import com.xiatian.mallmember.entity.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallmember.utils.PageUtils;

import java.util.Map;

/**
* @author XT189
* @description 针对表【ums_member_level(会员等级)】的数据库操作Service
* @createDate 2023-11-08 12:55:42
*/
public interface MemberLevelService extends IService<MemberLevel> {

    PageUtils findLevel(Map<String, Object> params);

    MemberLevel getDefaultLevel();
}
