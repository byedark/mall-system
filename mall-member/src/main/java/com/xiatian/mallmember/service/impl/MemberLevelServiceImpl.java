package com.xiatian.mallmember.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallmember.entity.MemberLevel;
import com.xiatian.mallmember.service.MemberLevelService;
import com.xiatian.mallmember.mapper.MemberLevelMapper;
import com.xiatian.mallmember.utils.PageUtils;
import com.xiatian.mallmember.utils.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author XT189
* @description 针对表【ums_member_level(会员等级)】的数据库操作Service实现
* @createDate 2023-11-08 12:55:42
*/
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel>
    implements MemberLevelService{

    @Resource
    MemberLevelMapper memberLevelMapper;

    @Override
    public PageUtils findLevel(Map<String, Object> params) {
        //全表查询极其危险，务必保证这个表不是很大，级联和全表查询都是危险行为
        List<MemberLevel> memberLevels = memberLevelMapper.selectList(null);
        //这里面实际上就是一个分页参数抽离然后封装到Page里的一个过程，本质上就是借助了Query参数，在Page里面会使用Wrapper进行参数过滤
        IPage<MemberLevel> page = this.page(new Query<MemberLevel>().getPage(params),null);
        //返回十条没有额外字段，不需要设置VO对象
        page.setRecords(memberLevels);
        return new PageUtils(page);
    }
}




