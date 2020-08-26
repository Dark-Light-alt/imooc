package com.imooc.aspect;

import com.imooc.exception.ApiException;
import com.imooc.utils.common.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerExceptionAspect {

    @Pointcut("execution(* com.imooc.controller.*.* (..))")
    private void aspect() {
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Result result = new Result();

        try {
            return proceedingJoinPoint.proceed();
        } catch (ApiException e) {
            result.error(500, e.msg());
        } catch (Exception e) {
            e.printStackTrace();
            result.error(500, "服务器开小差了 ^_^");
        }

        return result;
    }
}
