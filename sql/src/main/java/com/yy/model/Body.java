package com.yy.model;

import lombok.Builder;
import lombok.Data;

/**
 * description
 *
 * @author yangzai
 * @data 2020/11/20
 */
@Data
@Builder
public class Body {

    private String record;

    private int count;

    private String ip;

    private String port;

    private String username;

    private String password;

    private String url;

    private String driver;

    private String table;

    private String database;

    private String type;

}
