package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.Attr;
import com.xiatian.mallproduct.entity.AttrGroup;
import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.service.AttrGroupService;
import com.xiatian.mallproduct.mapper.AttrGroupMapper;
import com.xiatian.mallproduct.service.AttrService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.Query;
import com.xiatian.mallproduct.vo.AttrGroupWithAttrsVo;
import com.xiatian.mallproduct.vo.SpuItemAttrGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
* @author XT189
* @description 针对表【pms_attr_group(属性分组)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/

@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup>
    implements AttrGroupService{

    @Resource
    AttrGroupMapper attrGroupMapper;
    @Resource
    AttrService attrService;
    @Override
    public PageUtils queryPage(HashMap<String, Object> hashMap, Long catelogId) {
        //在工具类Query中已经将所有的情况全部考虑到了，如果这里直接总hashMap里面读，需要
        //会利用语法糖进行类型转换，在Java对基本数据类型进行拆箱的时候容易出现空指针异常
        //SQL语句需要考虑是否会出现SQL注入的情况
        //这里面isEmpty Spring框架已经启用改为了hasLength和hasText
        //这里如果用toString会报空指针异常
        String key = (String)hashMap.get("key");
        LambdaQueryWrapper<AttrGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.hasText(key)){
            lambdaQueryWrapper.like(AttrGroup::getAttrGroupName,key).or().eq(AttrGroup::getAttrGroupId,key);
        }
        IPage<AttrGroup> page;
        if(catelogId==0){
            page = this.page(new Query<AttrGroup>().getPage(hashMap),lambdaQueryWrapper);
            return new PageUtils(page);
        }
        else {
            lambdaQueryWrapper.eq(AttrGroup::getAttrGroupId, catelogId);
            page = this.page(new Query<AttrGroup>().getPage(hashMap),lambdaQueryWrapper);
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //1、查询分组信息
        List<AttrGroup> attrGroupEntities = this.list(new QueryWrapper<AttrGroup>()
                                                .eq("catelog_id", catelogId));

        //2、查询所有属性
        return attrGroupEntities.stream().map(group -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group,attrGroupWithAttrsVo);

            List<Attr> attrs = attrService.getRelationAttr(attrGroupWithAttrsVo.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrs);

            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SpuItemAttrGroup> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
        // 1.出当前Spu对应的所有属性的分组信息 以及当前分组下所有属性对应的值
        // 1.1 查询所有分组
        AttrGroupMapper baseMapper = this.getBaseMapper();
        return baseMapper.getAttrGroupWithAttrsBySpuId(spuId, catalogId);
    }
}




