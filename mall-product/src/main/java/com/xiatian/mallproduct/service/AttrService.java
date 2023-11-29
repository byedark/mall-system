package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.Attr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.vo.AttrGroupRelationVo;
import com.xiatian.mallproduct.vo.AttrRespVo;
import com.xiatian.mallproduct.vo.AttrVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @author XT189
* @description 针对表【pms_attr(商品属性)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface AttrService extends IService<Attr> {

    void saveVo(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> mp, Long catelogId, String attr);

    AttrRespVo getAttrInfo(Long attrId);

    @Transactional(rollbackFor = Exception.class)
    void updateAttrById(AttrVo attr);

    List<Attr> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String,Object> params, Long attrgroupId);
}
