package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.AttrAttrgroupRelation;
import com.xiatian.mallproduct.service.AttrAttrgroupRelationService;
import com.xiatian.mallproduct.mapper.AttrAttrgroupRelationMapper;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.Query;
import com.xiatian.mallproduct.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author XT189
* @description 针对表【pms_attr_attrgroup_relation(属性&属性分组关联)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationMapper, AttrAttrgroupRelation>
    implements AttrAttrgroupRelationService{

    /**
     * 批量添加属性与分组关联关系
     */
    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;
    @Override
    public void saveBatchRelation(List<AttrGroupRelationVo> vos) {
        List<AttrAttrgroupRelation> collect = vos.stream().map((item) -> {
            AttrAttrgroupRelation relationEntity = new AttrAttrgroupRelation();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationService.saveBatch(collect);
    }

}




