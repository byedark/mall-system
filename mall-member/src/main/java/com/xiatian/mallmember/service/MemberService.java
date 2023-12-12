package com.xiatian.mallmember.service;

import com.xiatian.mallmember.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallmember.vo.UserRegistVo;

/**
* @author XT189
* @description 针对表【ums_member(会员)】的数据库操作Service
* @createDate 2023-11-08 12:55:42
*/
public interface MemberService extends IService<Member> {
    void register(UserRegistVo vo);

    boolean checkPhoneUnique(String phone);

    boolean checkUserNameUnique(String userName);
}
