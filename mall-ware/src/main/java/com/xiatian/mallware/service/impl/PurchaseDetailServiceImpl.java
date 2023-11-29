package com.xiatian.mallware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallware.entity.PurchaseDetail;
import com.xiatian.mallware.service.PurchaseDetailService;
import com.xiatian.mallware.mapper.PurchaseDetailMapper;
import com.xiatian.mallware.utils.PageUtils;
import com.xiatian.mallware.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
* @author XT189
* @description 针对表【wms_purchase_detail】的数据库操作Service实现
* @createDate 2023-11-08 12:32:54
*/
@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetail>
    implements PurchaseDetailService{
    /**
     * 采购需求模糊查询
     * status: 0
     * wareId: 1
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetail> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(StringUtils.hasText(key)){
            // 连续拼接
            wrapper.and(w-> w.eq("purchase_id",key).or().eq("sku_id",key));
        }

        String status = (String) params.get("status");
        if(StringUtils.hasText(status)){
            wrapper.eq("status", status);
        }
        String wareId = (String) params.get("wareId");
        if(StringUtils.hasText(wareId)){
            wrapper.eq("ware_id", wareId);
        }

        IPage<PurchaseDetail> page = this.page(
                new Query<PurchaseDetail>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 根据采购单id 改变采购项
     */
    @Override
    public List<PurchaseDetail> listDetailByPurchaseId(Long id) {
        // 获取所有采购项
        return this.list(new QueryWrapper<PurchaseDetail>().eq("purchase_id", id));
    }
}




