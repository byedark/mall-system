package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.*;
import com.xiatian.mallproduct.mapper.AttrAttrgroupRelationMapper;
import com.xiatian.mallproduct.mapper.AttrGroupMapper;
import com.xiatian.mallproduct.mapper.CategoryMapper;
import com.xiatian.mallproduct.service.AttrAttrgroupRelationService;
import com.xiatian.mallproduct.service.AttrService;
import com.xiatian.mallproduct.mapper.AttrMapper;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.Query;
import com.xiatian.mallproduct.vo.AttrGroupRelationVo;
import com.xiatian.mallproduct.vo.AttrRespVo;
import com.xiatian.mallproduct.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    AttrMapper attrMapper;

    @Resource
    CategoryService categoryService;
    @Resource
    CategoryMapper categoryMapper;

    @Resource
    AttrGroupMapper attrGroupMapper;

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

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> mp, Long catelogId, String attr) {
        LambdaQueryWrapper<Attr> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(catelogId!=0L){
            System.out.println("ID查询销售属性");
            lambdaQueryWrapper.eq(Attr::getCatelogId,catelogId);
        }

        if(attr.equals("base")){
            lambdaQueryWrapper.eq(Attr::getAttrType,1);
        }
        else{
            lambdaQueryWrapper.eq(Attr::getAttrType,0);
        }
        String key = (String) mp.get("key");
        if(StringUtils.hasText(key)){
            lambdaQueryWrapper.like(Attr::getAttrName,key).or().eq(Attr::getAttrId,key);
        }
        //Utils是为了封装出来Page中所需要的参数一起加上去
        IPage<Attr> page = this.page(new Query<Attr>().getPage(mp),lambdaQueryWrapper);
        //List<Attr> result = attrMapper.selectByCatelogId(catelogId);
        List<Attr> list = page.getRecords();
        PageUtils pageUtils = new PageUtils(page);
        List<AttrRespVo> attrResp = list.stream().map(entity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(entity,attrRespVo);
            //把里面每一个都转换成最后的结果
            //需要先对数据库进行查询
            Category category = categoryMapper.selectById(entity.getCatelogId());
            if(category!=null){
                attrRespVo.setCatelogName(category.getName());
            }

            //继续查分组的名字
            LambdaQueryWrapper<AttrAttrgroupRelation> lambdaQueryWrapper1
                    = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(AttrAttrgroupRelation::getAttrId,entity.getAttrId());
            AttrAttrgroupRelation attrAttrgroupRelation= attrAttrgroupRelationMapper.selectOne(lambdaQueryWrapper1);
            if(attrAttrgroupRelation!=null){
                String groupName = attrGroupMapper.selectById(attrAttrgroupRelation.getAttrGroupId())
                        .getAttrGroupName();
                attrRespVo.setGroupName(groupName);
            }

            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(attrResp);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        //查询详细信息
        Attr attrEntity = this.getById(attrId);
        //查询分组信息
        AttrRespVo respVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,respVo);
        //判断是否是基本类型
        //1、设置分组信息
        AttrAttrgroupRelation relationEntity = attrAttrgroupRelationMapper.selectOne
                (new QueryWrapper<AttrAttrgroupRelation>().eq("attr_id", attrId));
        if (relationEntity != null) {
            respVo.setAttrGroupId(relationEntity.getAttrGroupId());
            //获取分组名称
            AttrGroup attrGroupEntity = attrGroupMapper.selectById(relationEntity.getAttrGroupId());
            if (attrGroupEntity != null) {
                respVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }
        //2、设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        List<Long> parentPath = new ArrayList<>(3);
        List<Long> catelogPath = categoryService.findParentCategory(catelogId,parentPath);
        Collections.reverse(catelogPath);
        respVo.setCatelogPath(catelogPath.toArray(new Long[0]));
        Category categoryEntity = categoryMapper.selectById(catelogId);
        if (categoryEntity != null) {
            respVo.setCatelogName(categoryEntity.getName());
        }
        return respVo;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAttrById(AttrVo attr) {
        Attr attrEntity = new Attr();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        //1、修改分组关联
        AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
        attrAttrgroupRelation.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelation.setAttrId(attr.getAttrId());

        Integer count = attrAttrgroupRelationMapper.selectCount(new QueryWrapper<AttrAttrgroupRelation>()
                .eq("attr_id", attr.getAttrId()));

        if (count > 0) {
            attrAttrgroupRelationMapper.update(attrAttrgroupRelation,
                    new UpdateWrapper<AttrAttrgroupRelation>().eq("attr_id",attr.getAttrId()));
        } else {
            attrAttrgroupRelationMapper.insert(attrAttrgroupRelation);
        }

    }

    @Override
    public List<Attr> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelation> entities = attrAttrgroupRelationMapper.selectList
                (new QueryWrapper<AttrAttrgroupRelation>().eq("attr_group_id", attrgroupId));

        List<Long> attrIds = entities.stream().map(AttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
        //根据attrIds查找所有的属性信息
        //Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        //如果attrIds为空就直接返回一个null值出去
        if (attrIds.size() == 0) {
            return null;
        }
        return this.baseMapper.selectBatchIds(attrIds);
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        //将数据封装到一个集合里然后对其进行遍历
        List<AttrAttrgroupRelation> entities = Arrays.stream(vos).map((item) -> {
            AttrAttrgroupRelation relationEntity = new AttrAttrgroupRelation();
            //要加Data不然会导致这里copy找不到get和set方法失效
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        System.out.println(entities);
        attrAttrgroupRelationMapper.deleteBatchRelation(entities);
    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrGroup attrGroupEntity = attrGroupMapper.selectById(attrgroupId);
        //获取当前分类的id
        Long catelogId = attrGroupEntity.getCatelogId();

        //2、当前分组只能关联别的分组没有引用的属性
        //2.1）、当前分类下的其它分组
        List<AttrGroup> groupEntities = attrGroupMapper.selectList(new QueryWrapper<AttrGroup>()
                .eq("catelog_id", catelogId));

        //获取到所有的attrGroupId
        List<Long> collect = groupEntities.stream().
                map(AttrGroup::getAttrGroupId).collect(Collectors.toList());

        //2.2）、这些分组关联的属性
        List<AttrAttrgroupRelation> groupId = attrAttrgroupRelationMapper.selectList
                (new QueryWrapper<AttrAttrgroupRelation>().in("attr_group_id", collect));

        List<Long> attrIds = groupId.stream().
                map(AttrAttrgroupRelation::getAttrId).collect(Collectors.toList());

        //2.3）、从当前分类的所有属性移除这些属性
        QueryWrapper<Attr> queryWrapper = new QueryWrapper<Attr>()
                .eq("catelog_id", catelogId)
                .eq("attr_type",1);

        if (attrIds.size() > 0) {
            queryWrapper.notIn("attr_id", attrIds);
        }
        //判断是否有参数进行模糊查询
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            queryWrapper.and((w) -> {
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<Attr> page = this.page(new Query<Attr>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }
}




