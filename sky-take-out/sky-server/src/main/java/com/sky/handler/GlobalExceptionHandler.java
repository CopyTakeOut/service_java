package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    /*
    * 处理SQL异常
    * */
    @ExceptionHandler
    public  Result exceptionHandler(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
        log.error("异常信息：{}", sqlIntegrityConstraintViolationException.getMessage());
        String message = sqlIntegrityConstraintViolationException.getMessage();
        if (message.contains("Duplicate entry")){
          String[] split =  message.split(" ");
          String username = split[2];
          String msg = username + MessageConstant.ALREADY_EXISTS;
          return Result.error(msg);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
    @ExceptionHandler
    public  Result exceptionHandler(SQLSyntaxErrorException sqlSyntaxErrorException){
        log.error("异常信息：{}", sqlSyntaxErrorException.getMessage());
        String message = sqlSyntaxErrorException.getMessage();
        if (message.contains("Duplicate entry")){
            String[] split =  message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.SQL_ERROR;
            return Result.error(msg);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
