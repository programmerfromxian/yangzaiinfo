package com.yy.mgr.back.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yang
 * @date 2020/03/20 21:02
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static String beanToJson(Object bean) {
        String json = JSON.toJSONString(bean);
        return json;
    }

    public static <T> T jsonToBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> jsonToDataList(String json, Class<T> clazz) {
        List<JSONObject> jsonList = jsonToBean(json, List.class);
        List<T> informationList = new ArrayList<>(jsonList.size());
        jsonList.stream().forEach(jsonObject -> informationList.add(JSONObject.toJavaObject(jsonObject, clazz)));
        return informationList;
    }
}
