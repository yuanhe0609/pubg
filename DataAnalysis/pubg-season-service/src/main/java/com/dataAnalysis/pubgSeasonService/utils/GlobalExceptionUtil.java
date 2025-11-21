package com.dataAnalysis.pubgSeasonService.utils;

import com.dataAnalysis.pubgCommonService.entity.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionUtil {
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 记录日志
        return Result.error("系统异常：" + e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleValidationException(ConstraintViolationException e) {
        return Result.error("参数验证失败：" + e.getMessage());
    }
}
