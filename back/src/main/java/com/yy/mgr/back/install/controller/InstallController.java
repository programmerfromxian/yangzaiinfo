package com.yy.mgr.back.install.controller;

import com.jcraft.jsch.JSchException;
import com.yy.mgr.back.install.InstallPO;
import com.yy.mgr.back.install.InstallPackageUtils;
import com.yy.mgr.back.install.SftpUtils;
import com.yy.mgr.back.model.CommonReturn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

/**
 * description
 *
 * @author yangzai
 * @data 2020/05/12
 */
@Controller
@RequestMapping("/install")
public class InstallController {

    @RequestMapping
    @ResponseBody
    public CommonReturn install(@RequestBody InstallPO installPO) throws IOException, JSchException {

        System.out.println(installPO);

        SftpUtils sftpUtils = new SftpUtils(installPO.getIp(), installPO.getPort(), installPO.getUsername(), installPO.getPassword());
        sftpUtils.uploadFile(installPO.getFilePath());
        InstallPackageUtils.install(installPO.getFilePath().substring(installPO.getFilePath().lastIndexOf(File.separator) + 1), sftpUtils);

        return CommonReturn.create("success");
    }
}
