package com.xiatian.mallmember.mapper;

import com.xiatian.mallmember.entity.MemberLevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author XT189
* @description 针对表【ums_member_level(会员等级)】的数据库操作Mapper
* @createDate 2023-11-08 12:55:42
* @Entity com.xiatian.mallmember.entity.MemberLevel
*/
public interface MemberLevelMapper extends BaseMapper<MemberLevel> {

    MemberLevel getDefault();
}




