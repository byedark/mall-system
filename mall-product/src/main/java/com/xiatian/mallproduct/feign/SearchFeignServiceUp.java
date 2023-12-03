package com.xiatian.mallproduct.feign;

import com.xiatian.mallproduct.to.SkuEsModel;
import com.xiatian.mallproduct.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@FeignClient("search")
public interface SearchFeignServiceUp {
    @PostMapping("search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
