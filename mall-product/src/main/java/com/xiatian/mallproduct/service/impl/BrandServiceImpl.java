package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.service.BrandService;
import com.xiatian.mallproduct.mapper.BrandMapper;
import org.springframework.stereotype.Service;

/**
* @author XT189
* @description 针对表【pms_brand(品牌)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand>
    implements BrandService{

}




