package com.xiatian.mallproduct.mapper;

import com.xiatian.mallproduct.entity.AttrGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiatian.mallproduct.vo.SpuItemAttrGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_attr_group(属性分组)】的数据库操作Mapper
* @createDate 2023-11-07 15:02:23
* @Entity com.xiatian.mallproduct.entity.AttrGroup
*/
public interface AttrGroupMapper extends BaseMapper<AttrGroup> {

    List<SpuItemAttrGroup> getAttrGroupWithAttrsBySpuId(@Param("spuId")Long spuId, @Param("catalogId")Long catalogId);
}




