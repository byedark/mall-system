package com.xiatian.mallproduct.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class SkuEsModel {
    private Long skuId;
    private Long spuId;
    //用来做全文检索
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;
    private Long saleCount;
    /**
     * 是否有库存
     */
    private Boolean hasStock;
    /**
     * 热度
     */
    private Long hotScore;
    private Long brandId;
    private Long catalogId;
    private String brandName;
    private String brandImg;
    private String catalogName;
    private List<Attrs> attrs;
    @Data
    public static class Attrs {
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}