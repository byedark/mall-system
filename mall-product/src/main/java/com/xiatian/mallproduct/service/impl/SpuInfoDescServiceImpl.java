package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.SpuInfoDesc;
import com.xiatian.mallproduct.service.SpuInfoDescService;
import com.xiatian.mallproduct.mapper.SpuInfoDescMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【pms_spu_info_desc(spu信息介绍)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescMapper, SpuInfoDesc>
    implements SpuInfoDescService{
    @Override
    public void saveSpuInfoDesc(SpuInfoDesc spuInfoDescEntity) {

        this.baseMapper.insert(spuInfoDescEntity);

    }
}




