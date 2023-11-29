package com.xiatian.mallproduct.mapper;

import com.xiatian.mallproduct.entity.Attr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_attr(商品属性)】的数据库操作Mapper
* @createDate 2023-11-07 15:02:23
* @Entity com.xiatian.mallproduct.entity.Attr
*/
public interface AttrMapper extends BaseMapper<Attr> {

    List<Attr> selectByCatelogId(@Param("catelogId") Long catelogId);

}




