package com.xiatian.mallproduct.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AttrRespVo extends AttrVo{
    //仔细思考不出现连表查询的问题所在
    //连表查询会导致十分庞大的数据量,一个表作为驱动表另外一个表进行查询，如果最后都无奈使用了全表搜索，服务直接崩了
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;

}
