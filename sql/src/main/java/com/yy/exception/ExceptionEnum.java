package com.yy.exception;

/**
 * @author yan9
 * @date 2019/09/16 23:03
 */
public enum ExceptionEnum implements CommonError {

    // 通用错误类型
    DATABASE_ERROR(10001, "数据库信息错误"),
    COUNT_ERROR(10002, "请输入count"),
    COUNT_LARGE(10003, "太多了"),
    RECORD_ERROR(10004, "核对输入的列"),
    UNKNOWN_ERROR(10000, "i am sorry")
    ;


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
