package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.SkuImages;
import com.xiatian.mallproduct.entity.SkuInfo;
import com.xiatian.mallproduct.entity.SpuInfoDesc;
import com.xiatian.mallproduct.mapper.SkuInfoMapper;
import com.xiatian.mallproduct.service.*;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.Query;
import com.xiatian.mallproduct.utils.R;
import com.xiatian.mallproduct.vo.ItemSaleAttrVo;
import com.xiatian.mallproduct.vo.SkuItemVo;
import com.xiatian.mallproduct.vo.SpuItemAttrGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
    implements SkuInfoService {

    @Autowired
    private SkuImagesService imagesService;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    /**
     * 自定义线程串池
     */
    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public void saveSkuInfo(SkuInfo skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondtion(Map<String, Object> params) {
        LambdaQueryWrapper<SkuInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            lambdaQueryWrapper.and((w) -> {
                w.eq(SkuInfo::getSkuId,key).or().like(SkuInfo::getSkuName,key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.hasText(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            lambdaQueryWrapper.eq(SkuInfo::getCatalogId,catelogId);
        }

        String brandId = (String) params.get("brandId");
        if (StringUtils.hasText(brandId) && !"0".equalsIgnoreCase(brandId)) {
            lambdaQueryWrapper.eq(SkuInfo::getBrandId,brandId);
        }

        String min = (String) params.get("min");
        if (StringUtils.hasText(min)) {
            lambdaQueryWrapper.ge(SkuInfo::getPrice,min);
        }

        String max = (String) params.get("max");
        if (StringUtils.hasText(max)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(max);
                if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
                    lambdaQueryWrapper.le(SkuInfo::getPrice,max);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IPage<SkuInfo> page = this.page(new Query<SkuInfo>().getPage(params));
        return new PageUtils(page);
    }

    @Override
    public List<SkuInfo> getSkusBySpuId(Long spuId) {
        return this.list(new LambdaQueryWrapper<SkuInfo>().eq(SkuInfo::getSpuId,spuId));
    }

    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = new SkuItemVo();

        CompletableFuture<SkuInfo> infoFutrue = CompletableFuture.supplyAsync(() -> {
            //1 sku基本信息
            SkuInfo info = getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, executor);

        CompletableFuture<Void> ImgageFuture = CompletableFuture.runAsync(() -> {
            //2 sku图片信息
            List<SkuImages> images = imagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(images);
        }, executor);
        //先要根据sku拿到spuId，异步编排
        CompletableFuture<Void> saleAttrFuture =infoFutrue.thenAcceptAsync(res -> {
            //3 获取spu销售属性组合
            List<ItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(res.getSpuId());
            skuItemVo.setSaleAttr(saleAttrVos);
        },executor);

        CompletableFuture<Void> descFuture = infoFutrue.thenAcceptAsync(res -> {
            //4 获取spu介绍
            SpuInfoDesc spuInfo = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDesc(spuInfo);
        },executor);

        CompletableFuture<Void> baseAttrFuture = infoFutrue.thenAcceptAsync(res -> {
            //5 获取spu规格参数信息
            List<SpuItemAttrGroup> attrGroups = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(), res.getCatalogId());
            System.out.println("++++++"+attrGroups.toString());
            skuItemVo.setGroupAttrs(attrGroups);
        }, executor);

        // 等待所有任务都完成再返回
        CompletableFuture.allOf(ImgageFuture,saleAttrFuture,descFuture,baseAttrFuture).get();
        return skuItemVo;
    }
}




