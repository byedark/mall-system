package com.xiatian.mallmember.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallmember.entity.Member;
import com.xiatian.mallmember.service.MemberService;
import com.xiatian.mallmember.mapper.MemberMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【ums_member(会员)】的数据库操作Service实现
* @createDate 2023-11-08 12:55:42
*/
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
    implements MemberService{

}




