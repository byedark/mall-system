package com.xiatian.mallware.control;

import com.xiatian.mallware.entity.PurchaseDetail;
import com.xiatian.mallware.service.PurchaseDetailService;
import com.xiatian.mallware.utils.PageUtils;
import com.xiatian.mallware.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("purchasedetail")
public class PurchaseDetailController {

    @Resource
    private PurchaseDetailService purchaseDetailService;

    /**
     * 	采购需求模糊查询
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseDetailService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        PurchaseDetail purchaseDetail = purchaseDetailService.getById(id);
        return R.ok().put("purchaseDetail", purchaseDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PurchaseDetail purchaseDetail){
        purchaseDetailService.save(purchaseDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PurchaseDetail purchaseDetail){
        purchaseDetailService.updateById(purchaseDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        purchaseDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
