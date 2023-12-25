package com.xiatian.mallsearch.service.impl;


import com.alibaba.fastjson.JSON;
import com.xiatian.mallsearch.config.ElasticSearchConfig;
import com.xiatian.mallsearch.constant.EsConstant;
import com.xiatian.mallsearch.service.ProductSaveService;
import com.xiatian.mallsearch.to.SkuEsModel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for(SkuEsModel model : skuEsModels){
            //不断保存每一个model数据，使得每一个model数据都成为一个倒排索引的文档
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).
                map(BulkItemResponse::getId).collect(Collectors.toList());
        log.warn("商品上架完成：{}", collect);
        return b;
    }
}
