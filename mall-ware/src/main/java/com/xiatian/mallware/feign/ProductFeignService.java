package com.xiatian.mallware.feign;

import com.xiatian.mallware.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("product")
public interface ProductFeignService {

    /**
     *  远程调用 product 获取商品名称
     *   1)、让所有请求过网关；
     *   2）、直接让后台指定服务处理
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
