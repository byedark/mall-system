package com.xiatian.mallproduct.vo;

import com.xiatian.mallproduct.entity.Attr;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrsVo {
    Long attrGroupId;
    String attrGroupName;
    Long sort;
    String descript;
    String icon;
    Long catelogId;
    List<Attr> attrs;
}
