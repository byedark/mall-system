package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.Attr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.vo.AttrVo;

/**
* @author XT189
* @description 针对表【pms_attr(商品属性)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface AttrService extends IService<Attr> {

    void saveVo(AttrVo attr);
}
