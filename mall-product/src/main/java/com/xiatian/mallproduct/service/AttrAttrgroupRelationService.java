package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.AttrAttrgroupRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.vo.AttrGroupRelationVo;

import java.util.List;

/**
* @author XT189
* @description 针对表【pms_attr_attrgroup_relation(属性&属性分组关联)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelation> {

    void saveBatchRelation(List<AttrGroupRelationVo> vos);
}
