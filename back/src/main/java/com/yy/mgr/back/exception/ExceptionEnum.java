package com.yy.mgr.back.exception;

/**
 * @author yan9
 * @date 2019/09/16 23:03
 */
public enum ExceptionEnum implements CommonError {

    // 通用错误类型
    PARAMETER_VALIDATION_ERROR(10001, "invalidate parameters"),
    UNKNOWN_ERROR(10002, "unknown error"),
    READ_FILE_ERROR(10003, "read data file error"),
    WRITE_FILE_ERROR(10004, "write information to file fail"),
    DELETE_INFORMATION_FAIL(20001, "delete fail"),
    CREATE_FILE_ERROR(10004, "create data file error"),
    EDIT_INFORMATION_FAIL(30001, "edit information error"),
    ADD_INFORMATION_ERROR(40001, "add information error"),
    INVALIDATE_PORT(50001, "port invalidate"),
    INVALIDATE_IP(50002, "ip invalidate"),
    INVALIDATE_DIR_NAME(50003, "dir not exists"),
    MK_DIR_ERROR(50004, "make dir fail"),
    ASSERT_NULL(60000, "assert null"),
    SFTP_LOGIN_ERROR(60001, "sftp login fail"),
    SFTP_ERROR(60002, "sftp error");


    ExceptionEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        // todo:关于this关键字的理解。this代表当前对象，当前对象继承CommonErr，所以返回值可以是当前对象
        return this;
    }
}
