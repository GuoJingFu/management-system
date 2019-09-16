package priv.linyu.manage.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @className: GlobalExceptionHandler
 * @author: QiuShangLin
 * @description: 全局异常助处理器
 * @date: 2019/7/19 0019 21:22
 * @version: 1.0
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String errorHandler(Exception e){
        log.error(e.getMessage());
        return "redirect:/error/500";
    }
}
