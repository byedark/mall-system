package com.xiatian.mallproduct.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttrVo{

    //可以多加一个属性分组的Id
    private Long attrGroupId;

    private Long attrId;

    private String attrName;

    private Integer searchType;

    private Integer valueType;

    private String icon;

    private String valueSelect;

    private Integer attrType;

    private Long enable;

    private Long catelogId;

    private Integer showDesc;

}
