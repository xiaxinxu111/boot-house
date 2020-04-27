package com.etoak.exception;

import com.etoak.commons.CommonsResult;
import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ParamException.class)
    public ModelAndView handleParamException(ParamException e){
        log.error(e.getMessage(),e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error",e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public CommonsResult handleUserLoginException(UserLoginException e){
        log.error(e.getMessage(),e);
        return new CommonsResult(CommonsResult.FAILED_CODE,e.getMessage());
    }
}
