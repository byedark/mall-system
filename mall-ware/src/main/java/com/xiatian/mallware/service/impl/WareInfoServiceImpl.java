package com.xiatian.mallware.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallware.entity.WareInfo;
import com.xiatian.mallware.service.WareInfoService;
import com.xiatian.mallware.mapper.WareInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【wms_ware_info(仓库信息)】的数据库操作Service实现
* @createDate 2023-11-08 12:32:54
*/
@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfo>
    implements WareInfoService{

}




