package com.xiatian.mallmember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallmember.entity.Member;
import com.xiatian.mallmember.entity.MemberLevel;
import com.xiatian.mallmember.exception.ExistException;
import com.xiatian.mallmember.service.MemberLevelService;
import com.xiatian.mallmember.service.MemberService;
import com.xiatian.mallmember.mapper.MemberMapper;
import com.xiatian.mallmember.vo.UserRegistVo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author XT189
* @description 针对表【ums_member(会员)】的数据库操作Service实现
* @createDate 2023-11-08 12:55:42
*/
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
    implements MemberService{

    @Resource
    MemberLevelService memberLevelService;
    @Override
    public void register(UserRegistVo vo) throws ExistException{
        //手机和昵称必须是唯一的
        boolean phoneUnique= this.checkPhoneUnique(vo.getPhone());
        boolean userNameUnique = this.checkUserNameUnique(vo.getUserName());
        //采用异常机制
        if(!phoneUnique || !userNameUnique){
            //感知异常
            throw new ExistException();
        }
        MemberMapper memberMapper = this.baseMapper;
        Member member = new Member();
        //设置默认的会员等级
        MemberLevel memberLevel = memberLevelService.getDefaultLevel();
        member.setLevelId(memberLevel.getId());

        //设置手机号
        member.setMobile(vo.getPhone());
        member.setNickname(vo.getUserName());

        //密码采用盐值加密技术，自带的库
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        member.setPassword(encode);
        memberMapper.insert(member);
    }

    @Override
    public boolean checkPhoneUnique(String phone) {
        MemberMapper memberMapper = this.baseMapper;
        Integer mobile = memberMapper.selectCount(new QueryWrapper<Member>().eq("mobile",phone));
        return mobile == 0;
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        MemberMapper memberMapper = this.baseMapper;
        Integer count = memberMapper.selectCount(new QueryWrapper<Member>().eq("username",userName));
        return count == 0;
    }
}




