package com.xiatian.authserver.aopInfo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class MyLog {
    
    //抽取公共的切入点表达式
    //该表达式表明MyService类下的所有方法都加上aop功能
    @Pointcut("execution(public int com.xiatian.authserver.*(..))")
    public void pointCut(){};
 
    //在目标方法之前切入
    //复用上面定义的pointCut()方法上的切入点表达式
//    @Before("pointCut()")
//    public void logStart(JoinPoint joinPoint){
//        //获取目标方法名 (单纯取出来而已下面没有用到这个变量)
//        String methodName = joinPoint.getSignature().getName();
//        //获取目标方法参数列表 （下面有输出这个）
//        Object[] args = joinPoint.getArgs();
//        System.out.println("除法运行...参数列表是: {" + Arrays.asList(args) + "}");
//    }
 
    //在目标方法之后切入
    @After("execution(*.* com.xiatian.authserver.web.LoginController.sendCode(..))")
    public void logEnd(JoinPoint joinPoint){
//        System.out.println();
    }
 
    //在目标方法正常返回切入
    //result是目标方法正常返回的返回值
//    @AfterReturning(value = "pointCut()", returning = "result")
//    public void logReturn(Object result){
//        System.out.println("除法正常返回...运行结果: { " + result + " }");
//    }
//    //在目标方法异常时切入
//    //exception是目标方法异常的异常对象
//    @AfterThrowing(value = "pointCut()", throwing = "exception")
//    public void logException(Exception exception){
//        System.out.println("除法异常...异常信息: {" + exception+ "}");
//    }
    
}