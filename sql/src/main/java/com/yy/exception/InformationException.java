package com.yy.exception;


import lombok.Data;

/**
 * @author yang
 * @date 2020/03/22 18:16
 */
@Data
public class InformationException extends RuntimeException {

    private CommonError commonError;

    public InformationException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }


}
