package com.yy.mgr.back.utils;

import com.yy.mgr.back.common.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yang
 * @date 2020/03/20 21:06
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static Boolean encryptDataAndWrite(String fileName, String data) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error("Create data file fail...", e);
                return Boolean.FALSE;
            }
        }
        // 加密后的字符串
        String encryptData = EncryptUtil.encryptData(data);
        try {
            // 文件中的json List<String>
            String jsonListString = FileUtils.readFileToString(file);
            // 文件中的json->List<json>
            List<String> jsonList = new ArrayList<>();
            if (!StringUtils.isEmpty(jsonListString)) {
                jsonList = JsonUtil.jsonToBean(jsonListString, List.class);
            }
            // 文件中的List添加
            jsonList.add(encryptData);
            // new List to Json
            String newJsonListString = JsonUtil.beanToJson(jsonList);
            // save new json to file
            FileUtils.writeStringToFile(file, newJsonListString);
        } catch (IOException e) {
            LOGGER.error("Write data to file fail...", e);
            return Boolean.FALSE;
        }
        LOGGER.info("Write data to file success...");
        return Boolean.TRUE;
    }

    /**
     * 获取数据存放的路径
     *
     * @return
     */
    public static String getDataFile() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty(Constants.DATA_FILE_DIR));
        sb.append(File.separator);
        sb.append(Constants.DATA_FILE_NAME);
        LOGGER.info("heheheheh {}", sb.toString());
        return sb.toString();
    }
}
