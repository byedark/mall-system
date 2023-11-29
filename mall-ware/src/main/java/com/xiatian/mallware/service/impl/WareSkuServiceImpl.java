package com.xiatian.mallware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallware.entity.WareInfo;
import com.xiatian.mallware.entity.WareSku;
import com.xiatian.mallware.feign.ProductFeignService;
import com.xiatian.mallware.service.WareSkuService;
import com.xiatian.mallware.mapper.WareSkuMapper;
import com.xiatian.mallware.utils.PageUtils;
import com.xiatian.mallware.utils.Query;
import com.xiatian.mallware.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author XT189
* @description 针对表【wms_ware_sku(商品库存)】的数据库操作Service实现
* @createDate 2023-11-08 12:32:54
*/
@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku>
    implements WareSkuService{

    @Resource
    WareSkuMapper wareSkuMapper;

    @Resource
    ProductFeignService productFeignService;
    @Override
    public Object getSkuList(Map<String, Object> map) {
        LambdaQueryWrapper<WareSku> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        String key = (String) map.get("key");
        if(StringUtils.hasText(key)){
            lambdaQueryWrapper.eq(WareSku::getId,key).or().like(WareSku::getSkuName,key);
        }
        IPage<WareSku> page = this.page(new Query<WareSku>().getPage(map),lambdaQueryWrapper);
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public double addStock(Long skuId, Long wareId, Integer skuNum) {
        // 1.如果还没有这个库存记录 那就是新增操作
        List<WareSku> entities = wareSkuMapper.selectList(new QueryWrapper<WareSku>().eq("sku_id", skuId).eq("ware_id", wareId));
        double price = 0.0;
        // TODO 还可以用什么办法让异常出现以后不回滚？高级
        WareSku entity = new WareSku();
        try {
            R info = productFeignService.info(skuId);
            Map<String,Object> data = (Map<String, Object>) info.get("skuInfo");

            if(info.getCode() == 0){
                entity.setSkuName((String) data.get("skuName"));
                // 设置商品价格
                price = (Double) data.get("price");
            }
        }catch (Exception e){
            System.out.println("com.firenay.mall.ware.service.impl.WareSkuServiceImpl：远程调用出错");
        }
        // 新增操作
        if(entities == null || entities.size() == 0){
            entity.setSkuId(skuId);
            entity.setStock(skuNum);
            entity.setWareId(wareId);
            entity.setStockLocked(0);
            wareSkuMapper.insert(entity);
        }else {
            wareSkuMapper.addStock(skuId, wareId, skuNum);
        }
        return price;
    }
}




