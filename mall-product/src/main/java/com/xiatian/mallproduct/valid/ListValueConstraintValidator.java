package com.xiatian.mallproduct.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

//这里涉及到了注解的源码细节，非常难
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {
    private final Set<Integer> set = new HashSet<>();

    /**
     * 初始化方法
     * 参数：自定义注解的详细信息
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.vals();
        for (int val : values) {
            set.add(val);
        }
    }

    /**
     * 判断是否校验成功
     *
     * @param value   需要校验的值
     * @param context 上下文信息
     * @return
     */

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}