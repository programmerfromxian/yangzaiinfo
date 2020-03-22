package com.yy.mgr.back.model;

import lombok.Data;

/**
 * @author yang
 * @date 2020/03/18 23:50
 */
@Data
public class CommonReturn {
    private Boolean success;
    private Integer errCode;
    private String errMsg;
    private Object returnData;

    private CommonReturn() {

    }

    private CommonReturn(Boolean success, Object returnData) {
        this.success = success;
        this.returnData = returnData;
    }

    private CommonReturn(Boolean success, Integer errCode, String errMsg, Object returnData) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.returnData = returnData;
    }

    public CommonReturn(Boolean success, Integer errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * 响应成功
     * @param success
     * @param returnData
     * @return
     */
    public static CommonReturn create(Boolean success, Object returnData) {
        return new CommonReturn(success, returnData);
    }

    public static CommonReturn create(Object returnData) {
        return new CommonReturn(true, returnData);
    }

    /**
     * 响应失败
     * @param success
     * @param errCode
     * @param errMsg
     * @param returnData
     * @return
     */
    public static CommonReturn create(Boolean success, Integer errCode, String errMsg, Object returnData) {
        return new CommonReturn(success, errCode, errMsg, returnData);
    }

    public static CommonReturn create(Integer errCode, String errMsg) {
        return new CommonReturn(Boolean.FALSE, errCode, errMsg);
    }
}
