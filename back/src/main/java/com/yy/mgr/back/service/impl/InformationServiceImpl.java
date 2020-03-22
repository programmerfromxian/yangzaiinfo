package com.yy.mgr.back.service.impl;

import com.yy.mgr.back.exception.ExceptionEnum;
import com.yy.mgr.back.exception.InformationException;
import com.yy.mgr.back.model.Information;
import com.yy.mgr.back.service.InformationService;
import com.yy.mgr.back.utils.EncryptUtil;
import com.yy.mgr.back.utils.FileUtil;
import com.yy.mgr.back.utils.JsonUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yang
 * @date 2020/03/18 23:59
 */
@Service
public class InformationServiceImpl implements InformationService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<Information> queryAll() throws InformationException {
        return getInformationListFromFile();
    }

    @Override
    public Boolean add(Information information) throws InformationException {
        List<Information> informationList = getInformationListFromFile();
        information.setId(informationList.size() + 1);
        informationList.add(information);
        writeListToFile(informationList);
        return Boolean.TRUE;
    }

    @Override
    public Boolean delete(Integer id) throws InformationException {
        // 读取文件中的json
        File dataFile = new File(FileUtil.getDataFile());
        List<Information> informationList = getInformationListFromFile();
        // 删除 ...并发修改
        for (Information information : informationList) {
            if (information.getId() == id) {
                informationList.remove(information);
                break;
            }
        }
        // 回写
        List<Information> newInformationList = new ArrayList<>();
        for (int i = 0; i < informationList.size(); i++) {
            Information information = informationList.get(i);
            information.setId(i + 1);
            newInformationList.add(information);
        }
        writeListToFile(newInformationList);
        return Boolean.TRUE;
    }

    @Override
    public Boolean update(Information information) throws InformationException {
        // 读取
        List<Information> informationList = getInformationListFromFile();
        // 修改
        for (int i = 0; i < informationList.size(); i++) {
            if (informationList.get(i).getId() == information.getId()) {
                informationList.set(i, information);
                break;
            }
        }
        // 回写
        writeListToFile(informationList);
        return Boolean.TRUE;
    }

    private void writeListToFile(List<Information> informationList) throws InformationException {
        File file = new File(FileUtil.getDataFile());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error("Error when create new file {}...", file);
                throw new InformationException(ExceptionEnum.CREATE_FILE_ERROR);
            }
        }
        String json = JsonUtil.beanToJson(informationList);
        String encryptJson = EncryptUtil.encryptData(json);
        try {
            FileUtils.writeStringToFile(file, encryptJson);
        } catch (IOException e) {
            LOGGER.error("Error when write json to file {}...", file);
            throw new InformationException(ExceptionEnum.WRITE_FILE_ERROR);
        }
    }

    private List<Information> getInformationListFromFile() throws InformationException {
        String path = FileUtil.getDataFile();
        File dataFile = new File(path);
        if (!dataFile.exists())
            return new ArrayList<>();
        String fileString = null;
        try {
            fileString = FileUtils.readFileToString(dataFile);
        } catch (IOException e) {
            LOGGER.error("Read file {} error, reason {} ...", dataFile, e);
            throw new InformationException(ExceptionEnum.READ_FILE_ERROR);
        }
        // 解密
        String decryptFileString = EncryptUtil.decryptData(fileString);
        return JsonUtil.jsonToDataList(decryptFileString, Information.class);
    }
}
