package com.xiatian.mallware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallware.entity.WareInfo;
import com.xiatian.mallware.service.WareInfoService;
import com.xiatian.mallware.mapper.WareInfoMapper;
import com.xiatian.mallware.utils.PageUtils;
import com.xiatian.mallware.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
* @author XT189
* @description 针对表【wms_ware_info(仓库信息)】的数据库操作Service实现
* @createDate 2023-11-08 12:32:54
*/
@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfo>
    implements WareInfoService{

    @Override
    public PageUtils getWare(Map<String, Object> map) {
        LambdaQueryWrapper<WareInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        String key = (String) map.get("key");
        if(StringUtils.hasText(key)){
            lambdaQueryWrapper.eq(WareInfo::getId,key).or().like(WareInfo::getName,key);
        }
        IPage<WareInfo> page = this.page(new Query<WareInfo>().getPage(map),lambdaQueryWrapper);
        return new PageUtils(page);
    }
}




