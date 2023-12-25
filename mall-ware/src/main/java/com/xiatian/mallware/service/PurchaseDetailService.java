package com.xiatian.mallware.service;

import com.xiatian.mallware.entity.PurchaseDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallware.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
* @author XT189
* @description 针对表【wms_purchase_detail】的数据库操作Service
* @createDate 2023-11-08 12:32:54
*/
public interface PurchaseDetailService extends IService<PurchaseDetail> {

    PageUtils queryPage(Map<String, Object> params);

    List<PurchaseDetail> listDetailByPurchaseId(Long id);
}
