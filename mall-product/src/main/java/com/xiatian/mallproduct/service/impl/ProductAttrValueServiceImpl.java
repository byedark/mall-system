package com.xiatian.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.ProductAttrValue;
import com.xiatian.mallproduct.service.ProductAttrValueService;
import com.xiatian.mallproduct.mapper.ProductAttrValueMapper;
import com.xiatian.mallproduct.utils.PageUtils;
import com.xiatian.mallproduct.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author XT189
* @description 针对表【pms_product_attr_value(spu属性值)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/

@Service
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValue>
    implements ProductAttrValueService{

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValue> page = this.page(
                new Query<ProductAttrValue>().getPage(params),
                new QueryWrapper<ProductAttrValue>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveProductAttr(List<ProductAttrValue> collect) {
        this.saveBatch(collect);
    }

    @Override
    public List<ProductAttrValue> baseAttrListforspu(Long spuId) {

        return this.baseMapper.selectList(
                new QueryWrapper<ProductAttrValue>().eq("spu_id", spuId));
    }


    /**
     * 修改商品规格
     * @param spuId
     * @param entities
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValue> entities) {
        //1、删除spuId之前对应的所有属性
        this.baseMapper.delete(new QueryWrapper<ProductAttrValue>().eq("spu_id",spuId));

        //2、添加商品规格信息
        List<ProductAttrValue> collect = entities.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());

        //批量新增
        this.saveBatch(collect);
    }

}




