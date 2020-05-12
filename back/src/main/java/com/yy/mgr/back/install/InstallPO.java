package com.yy.mgr.back.install;

import lombok.Data;

/**
 * description
 *
 * @author yangzai
 * @data 2020/05/13
 */
@Data
public class InstallPO {
    private String ip;
    private int port;
    private String username;
    private String password;
    private String ip1;
    private String port1;
    private String ip2;
    private String port2;
    private String name;
    private String id;
    private String sftpUsername;
    private String sftpPassword;
    private String filePath;
    private String param1;
    private String param2;
    private String param3;

    @Override
    public String toString() {
        return "InstallPO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ip1='" + ip1 + '\'' +
                ", port1='" + port1 + '\'' +
                ", ip2='" + ip2 + '\'' +
                ", port2='" + port2 + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", sftpUsername='" + sftpUsername + '\'' +
                ", sftpPassword='" + sftpPassword + '\'' +
                ", filePath='" + filePath + '\'' +
                ", param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                ", param3='" + param3 + '\'' +
                '}';
    }
}
