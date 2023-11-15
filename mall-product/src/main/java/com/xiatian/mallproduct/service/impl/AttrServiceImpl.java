package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.Attr;
import com.xiatian.mallproduct.entity.AttrAttrgroupRelation;
import com.xiatian.mallproduct.mapper.AttrAttrgroupRelationMapper;
import com.xiatian.mallproduct.service.AttrAttrgroupRelationService;
import com.xiatian.mallproduct.service.AttrService;
import com.xiatian.mallproduct.mapper.AttrMapper;
import com.xiatian.mallproduct.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author XT189
* @description 针对表【pms_attr(商品属性)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr>
    implements AttrService{

    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Resource
    AttrAttrgroupRelationMapper attrAttrgroupRelationMapper;

    @Override
    public void saveVo(AttrVo attr) {
        Attr attrEntity = new Attr();
        //利用Bean自带的填充函数，前面一个是source后面一个是target
        BeanUtils.copyProperties(attr,attrEntity);
        //由于传了一个这个属性属于哪一个分组，所以需要再关联一波分组
        AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
        attrAttrgroupRelation.setAttrId(attr.getAttrId());
        attrAttrgroupRelation.setAttrGroupId(attr.getAttrGroupId());
        //对两张表都进行保存，说白了由于外键不能用，所以一个关联表两个属性表，更新一个就要更新两张表格
        attrAttrgroupRelationMapper.insert(attrAttrgroupRelation);
        this.save(attrEntity);
    }
}




