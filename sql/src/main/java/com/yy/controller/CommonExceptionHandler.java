package com.yy.controller;


import com.yy.exception.CommonError;
import com.yy.exception.ExceptionEnum;
import com.yy.exception.InformationException;
import com.yy.model.CommonReturn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yang
 * @date 2020/03/19 22:10
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonReturn handler(Exception ex) {
        if (ex instanceof InformationException) {
            InformationException informationException = (InformationException) ex;
            CommonError commonError = informationException.getCommonError();
            return CommonReturn.create(commonError.getErrCode(), commonError.getErrMsg());
        } else {
            ex.printStackTrace();
            CommonError commonError = ExceptionEnum.UNKNOWN_ERROR;
            return CommonReturn.create(commonError.getErrCode(), commonError.getErrMsg());
        }
    }
}
