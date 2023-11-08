package com.xiatian.mallmember.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallmember.entity.MemberLoginLog;
import com.xiatian.mallmember.service.MemberLoginLogService;
import com.xiatian.mallmember.mapper.MemberLoginLogMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【ums_member_login_log(会员登录记录)】的数据库操作Service实现
* @createDate 2023-11-08 12:55:42
*/
@Service
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogMapper, MemberLoginLog>
    implements MemberLoginLogService{

}




