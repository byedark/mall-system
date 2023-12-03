package com.xiatian.mallproduct.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallproduct.entity.Category;
import com.xiatian.mallproduct.service.CategoryBrandRelationService;
import com.xiatian.mallproduct.service.CategoryService;
import com.xiatian.mallproduct.mapper.CategoryMapper;
import com.xiatian.mallproduct.utils.Result;
import com.xiatian.mallproduct.vo.Catalog3Vo;
import com.xiatian.mallproduct.vo.Catelog2Vo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author XT189
* @description 针对表【pms_category(商品三级分类)】的数据库操作Service实现
* @createDate 2023-11-07 15:02:23
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Resource
    CategoryMapper categoryMapper;

    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Category> listTree() {
        //直接进行全表搜索，不要递归查数据库，这会导致速度非常慢
        List<Category> listAll = categoryMapper.selectList(null);
        return listAll.stream().filter(listCategory -> listCategory.getParentCid()==0)
                .peek(listCategory -> listCategory
                        .setChildren(getChildrens(listCategory,listAll)))
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() ==null ? 0:menu1.getSort())- (menu2.getSort()==null?0:menu2.getSort());
                })
                .collect(Collectors.toList());
//        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        //一级列表限制查询个数为15条，建立索引进行二次优化
//        lambdaQueryWrapper.eq(Category::getParentCid,0).last("limit 15");
//        List<Category> list = categoryMapper.selectList(lambdaQueryWrapper);
//        list = list.stream().peek(listCategory -> listCategory
//                .setChildren(getChildrens(listCategory)))
//                .sorted(Comparator.comparingInt(Category::getSort))
//                .collect(Collectors.toList());
//        //形成父子关系，进行二级列表返回
    }

    @Override
    public Result<String> deleteCategory(List<Long> categoryId) {
        //对数据库进行操作,实现的是逻辑删除
        categoryMapper.deleteBatchIds(categoryId);
        return Result.ok("ok");
    }

    @Override
    public Long[] getByAttrGroupId(Long attr) {
        //从当前层，递归向前三次，找到当前分类的层级目录，获取到当前层级的目录并以数组的形式返回，因为三级分类嘛，
        List<Long> parentPath = new ArrayList<>(3);
        //不新开一个数组的话容易出现地址重新分配
        List<Long> result = findParentCategory(attr,parentPath);
        //static方法不能被继承，被基础类Collection所长有
        Collections.reverse(result);
        //Integer[] ans = list.toArray(new Integer[list.size()]); 输出指定类型的数组，输出的数组类型与括号中参数类型一致；
        //这里List转成数组的过程为什么一开始初始化为0呢，说白了是线程方面的安全问题
        return  result.toArray(new Long[0]);
    }

    //级联更新必须一起执行成功，不然数据不一致，一定要加事务
    @Transactional
    @Override
    public void updateDetail(Category category) {
        this.updateById(category);
        if(!StringUtils.isEmpty(category.getName())){
            //这里面前端没有传ID所以只能暂时用查字符串的方式代替，这样子会出现重复的字符串只能修改一个
//            LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(Category::getName,category.getName());
//            Long catId = categoryMapper.selectOne(lambdaQueryWrapper).getCatId();
            //同步更新其他关联表中的数据
            categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
        }
    }

    @Override
    public List<Long> findParentCategory(Long curCate, List<Long> result){
        result.add(curCate);
        Category pid = this.getById(curCate);
        Long parentCid = pid.getParentCid();
        if(parentCid!=0){
            //这里不需要传地址，这里传的是引用吗？
            findParentCategory(parentCid,result);
        }
        return result;
    }

    @Override
    public List<Category> getLevel1() {
        return baseMapper.selectList(new QueryWrapper<Category>().eq("parent_cid", 0));
    }
    @Override
    public Map<String, List<Catelog2Vo>> getCategory2(){
        String stringJson = stringRedisTemplate.opsForValue().get("catelogJSON");
        if(StringUtils.isEmpty(stringJson))
            return getCategory2Db();
        return JSON.parseObject(stringJson,new TypeReference<Map<String, List<Catelog2Vo>>>(){});
    }

    public Map<String, List<Catelog2Vo>> getCategory2Db() {
        synchronized (this) {
            String stringJson = stringRedisTemplate.opsForValue().get("catelogJSON");
            if(StringUtils.isEmpty(stringJson)) {
                System.out.println("查询了一次数据库");
                //先查出所有分类，然后再依次填入，如果循环查询数据库，查询次数太多容易导致性能下降。
                List<Category> allCategorys = baseMapper.selectList(null);
                List<Category> level1Categorys = getCategoryEntities(allCategorys, 0L);
                //封装成要返回的数据
                Map<String, List<Catelog2Vo>> cate = level1Categorys.stream().collect(Collectors.toMap(t -> t.getCatId().toString(), v -> {
                    List<Category> listCategory2 = getCategoryEntities(allCategorys, v.getCatId());
                    //封装里面的数组部分
                    return listCategory2.stream().map(item -> {
                        //初始化除了这个列表的其他列表信息
                        Catelog2Vo catelog2Vo = new Catelog2Vo(item.getCatId().toString(),
                                item.getName(), v.getCatId().toString(), null);
                        List<Category> category3 = getCategoryEntities(allCategorys, item.getCatId());
                        List<Catalog3Vo> catalog3Vos = category3.stream().
                                map(entity -> new Catalog3Vo(
                                        entity.getCatId().toString(), entity.getName(), item.getCatId().toString())).
                                collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                        return catelog2Vo;
                    }).collect(Collectors.toList());
                }));
                String s = JSON.toJSONString(cate);
                stringRedisTemplate.opsForValue().set("catelogJSON", s, 1, TimeUnit.HOURS);
                return cate;
            }else {
                return JSON.parseObject(stringJson,new TypeReference<Map<String, List<Catelog2Vo>>>(){});
            }
        }
    }

    private List<Category> getCategoryEntities(List<Category> entityList, Long parent_cid) {
        return entityList.stream().filter(item -> Objects.equals(item.getParentCid(), parent_cid)).collect(Collectors.toList());
    }

    public List<Category> getChildrens(Category parent,List<Category> listAll){
         return listAll.stream().filter(listCategory ->
                         Objects.equals(listCategory.getParentCid(), parent.getCatId()))
                .peek(listCategory -> listCategory
                        .setChildren(getChildrens(listCategory,listAll)))
                 .sorted((menu1, menu2) -> {
                     return (menu1.getSort() ==null ? 0:menu1.getSort())- (menu2.getSort()==null?0:menu2.getSort());
                 })
                .collect(Collectors.toList());
//        if(parent==null)
//            return null;
//        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Category::getParentCid,parent.getCatId());
//        List<Category> list = categoryMapper.selectList(lambdaQueryWrapper);
//        list = list.stream().peek(listCategory -> listCategory
//                        .setChildren(getChildrens(listCategory)))
//                .collect(Collectors.toList());
    }
}




