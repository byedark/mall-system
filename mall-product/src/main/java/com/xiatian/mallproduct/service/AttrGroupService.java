package com.xiatian.mallproduct.service;

import com.xiatian.mallproduct.entity.AttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.vo.AttrGroupWithAttrsVo;
import com.xiatian.mallproduct.vo.SpuItemAttrGroup;

import java.util.HashMap;
import java.util.List;

/**
* @author XT189
* @description 针对表【pms_attr_group(属性分组)】的数据库操作Service
* @createDate 2023-11-07 15:02:23
*/
public interface AttrGroupService extends IService<AttrGroup> {

    PageUtils queryPage(HashMap<String, Object> hashMap, Long catelogId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);

    List<SpuItemAttrGroup> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}
