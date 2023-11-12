package com.xiatian.mallproduct.exception;

import com.xiatian.mallproduct.utils.BizCodeEnume;
import com.xiatian.mallproduct.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.util.HashMap;

@Slf4j
@RestControllerAdvice(basePackages = "com.xiatian.mallproduct.controller")
public class MallExceptionControllerAdvice {
    //抛出的异常写在注解上
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleExceptions(MethodArgumentNotValidException e){
        log.error("格式校验错误");
        HashMap<String,String> hashMap = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach( (item)->{
            //返回的信息为：
            String a = item.getField();
            String b = item.getDefaultMessage();
            hashMap.put(a,b);
        });
        return R.error(BizCodeEnume.VALID_EXCEPTION.getCode(),
                BizCodeEnume.VALID_EXCEPTION.getMsg()).put("data",hashMap);
    }
    @ExceptionHandler(Throwable.class)
    public R handleExceptions(Throwable throwable){
        log.error("错误:",throwable);
        return R.error(BizCodeEnume.UNKNOWN_EXCEPTION.getCode(),
                BizCodeEnume.UNKNOWN_EXCEPTION.getMsg());
    }
}