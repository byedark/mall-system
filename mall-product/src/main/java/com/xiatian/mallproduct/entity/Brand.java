package com.xiatian.mallproduct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiatian.mallproduct.valid.AddApplication;
import com.xiatian.mallproduct.valid.ListValue;
import com.xiatian.mallproduct.valid.UpdateApplication;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 * @TableName pms_brand
 */
@TableName(value ="pms_brand")
public class Brand implements Serializable {

    /**
     * 品牌id
     */
    @NotNull(message = "修改品牌ID不能为空",groups = {UpdateApplication.class})
    @Null(message = "新增品牌时不能指定ID",groups = {AddApplication.class})
    @TableId(type = IdType.AUTO)
    private Long brandId;

    /**
     * 品牌名
     */
    @NotEmpty(message = "增加品牌时分组不能为空",groups = {AddApplication.class,UpdateApplication.class})
    private String name;

    /**
     * 品牌logo地址
     */
    //设置分组接口，这两个接口可以为空，主要目的就是为了配合volited接口使其可以利用接口生效
    @NotEmpty(message = "URL地址不能空",groups = {AddApplication.class})
    @URL(message = "不是一个合法的URL地址",groups = {AddApplication.class, UpdateApplication.class})
    private String logo;

    /**
     * 介绍
     */
    private String descript;

    /**
     * 显示状态[0-不显示；1-显示]
     */
    @ListValue(vals={0,1},groups = {AddApplication.class, UpdateApplication.class})
    @NotNull(groups = {AddApplication.class, UpdateApplication.class})
    private Integer showStatus;

    /**
     * 检索首字母
     */
    @NotEmpty(groups={AddApplication.class})
    @Pattern(regexp="^[a-zA-Z]$",message = "检索首字母必须是字母",groups =
            {AddApplication.class, UpdateApplication.class})
    private String firstLetter;

    /**
     * 排序
     */
    // NotNull常常和下面的MaxMin配合使用，因为下面的在上传为空的时候默认不生效
    @NotNull(groups={AddApplication.class})
    @Min(value = 0,message = "排序大于等于0",groups={AddApplication.class,UpdateApplication.class})
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * 品牌id
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * 品牌名
     */
    public String getName() {
        return name;
    }

    /**
     * 品牌名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 品牌logo地址
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 品牌logo地址
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 介绍
     */
    public String getDescript() {
        return descript;
    }

    /**
     * 介绍
     */
    public void setDescript(String descript) {
        this.descript = descript;
    }

    /**
     * 显示状态[0-不显示；1-显示]
     */
    public Integer getShowStatus() {
        return showStatus;
    }

    /**
     * 显示状态[0-不显示；1-显示]
     */
    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    /**
     * 检索首字母
     */
    public String getFirstLetter() {
        return firstLetter;
    }

    /**
     * 检索首字母
     */
    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    /**
     * 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Brand other = (Brand) that;
        return (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLogo() == null ? other.getLogo() == null : this.getLogo().equals(other.getLogo()))
            && (this.getDescript() == null ? other.getDescript() == null : this.getDescript().equals(other.getDescript()))
            && (this.getShowStatus() == null ? other.getShowStatus() == null : this.getShowStatus().equals(other.getShowStatus()))
            && (this.getFirstLetter() == null ? other.getFirstLetter() == null : this.getFirstLetter().equals(other.getFirstLetter()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLogo() == null) ? 0 : getLogo().hashCode());
        result = prime * result + ((getDescript() == null) ? 0 : getDescript().hashCode());
        result = prime * result + ((getShowStatus() == null) ? 0 : getShowStatus().hashCode());
        result = prime * result + ((getFirstLetter() == null) ? 0 : getFirstLetter().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", brandId=").append(brandId);
        sb.append(", name=").append(name);
        sb.append(", logo=").append(logo);
        sb.append(", descript=").append(descript);
        sb.append(", showStatus=").append(showStatus);
        sb.append(", firstLetter=").append(firstLetter);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}