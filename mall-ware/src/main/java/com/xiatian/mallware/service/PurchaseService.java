package com.xiatian.mallware.service;

import com.xiatian.mallware.entity.Purchase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiatian.mallware.utils.PageUtils;
import com.xiatian.mallware.vo.MergeVo;
import com.xiatian.mallware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
* @author XT189
* @description 针对表【wms_purchase(采购信息)】的数据库操作Service
* @createDate 2023-11-08 12:32:54
*/
public interface PurchaseService extends IService<Purchase> {

    void done(PurchaseDoneVo doneVo);

    void received(List<Long> ids);

    void mergePurchase(MergeVo mergeVo);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);
}
