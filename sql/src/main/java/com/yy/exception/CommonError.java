package com.yy.exception;

/**
 * @author yan9
 * @date 2019/09/16 23:02
 */
public interface CommonError {

    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
}
