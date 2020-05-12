package com.yy.mgr.back.install;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

/**
 * description
 *
 * @author yangzai
 * @data 2020/05/12
 */
public class SftpUtils {

    private static Logger logger = LoggerFactory.getLogger(SftpUtils.class);

    private ChannelSftp sftp;
    private Session session;

    /***
     * 连接sftp客户端
     * @param ip
     * @param port
     * @param userName
     * @param password
     * @return
     */
    public SftpUtils(String ip, int port, String userName, String password) {
        JSch jsch = new JSch();

        try {

            session = jsch.getSession(userName, ip, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.setProperty("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
            logger.info("sftp is connect ip {} success", session);
        } catch (JSchException e) {
            logger.info("sftp is error {}", e);
            e.printStackTrace();
        }
    }

    /**
     * sftp读取目录，按关键字获取文件名或者路径
     *
     * @param dir
     * @return
     */
    public Vector<ChannelSftp.LsEntry> readPath(String dir) {
        Vector<ChannelSftp.LsEntry> vr = null;
        try {
            vr = sftp.ls(dir);
            for (ChannelSftp.LsEntry file : vr) {
                String mtimeString = file.getAttrs().getMtimeString();
                if (file.getFilename().equals("aa.txt")) {
                    int mTime = file.getAttrs().getMTime();
                    String s = String.valueOf(mTime) + "000";
                    Long integer = Long.valueOf(s);
                    Date date = new Date();
                    date.setTime(integer);
                    System.out.println(file.getAttrs().getMtimeString());
                    System.out.println(date);
                }
            }
            logger.info("dir path is content {}", vr.toArray().toString());
        } catch (SftpException e) {
            logger.error("readPath is error {}", e);
            e.printStackTrace();
        }
        return vr;
    }

    /**
     * 上传文件
     *
     * @param filePath 文件名
     * @return
     */
    public boolean uploadFile(String filePath) {
        try {
            sftp.put(filePath, "/opt");
        } catch (SftpException e) {
            e.printStackTrace();
            return false;
        } finally {
            logout();
        }
        return true;
    }

    /**
     * 关闭sftp连接
     */
    public void logout() {
        if (sftp != null) {
            sftp.disconnect();
        }

        if (session != null) {
            session.disconnect();
        }
    }

    public static void main(String[] args) {

    }
}
