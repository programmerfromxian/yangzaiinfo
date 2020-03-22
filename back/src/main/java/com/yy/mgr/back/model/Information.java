package com.yy.mgr.back.model;

import lombok.Data;

/**
 * @author yang
 * @date 2020/03/18 23:26
 */
@Data
public class Information {

    private Integer id;
    private String type;
    private String name;
    private String account;
    private String password;
    private String description;
    private String url;
    private String lastUpdate;
    private String historyPassword;

}
