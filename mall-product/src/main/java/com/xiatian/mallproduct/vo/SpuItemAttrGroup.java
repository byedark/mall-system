package com.xiatian.mallproduct.vo;

import lombok.Data;

import java.util.List;

@Data
public class SpuItemAttrGroup{
    private String groupName;
    private List<SpuBaseAttrVo> attrs;
}