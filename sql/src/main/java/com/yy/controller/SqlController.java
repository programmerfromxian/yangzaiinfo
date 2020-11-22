package com.yy.controller;

import com.yy.common.DatasourceManage;
import com.yy.exception.ExceptionEnum;
import com.yy.model.Body;
import com.yy.model.CommonReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * description
 *
 * @author yangzai
 * @data 2020/11/20
 */
@Controller
@RequestMapping()
public class SqlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlController.class);

    @RequestMapping(method = RequestMethod.POST, path = "/insert")
    @ResponseBody
    public CommonReturn insert(@RequestBody Body body) throws Exception {
        if (body.getCount() <= 0) {
            return CommonReturn.create(ExceptionEnum.COUNT_ERROR.getErrCode(), ExceptionEnum.COUNT_ERROR.getErrMsg());
        }
        if (body.getCount() > 10000) {
            return CommonReturn.create(ExceptionEnum.COUNT_LARGE.getErrCode(), ExceptionEnum.COUNT_LARGE.getErrMsg());
        }
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DatasourceManage.getDataSource(body));
        int count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count(*) from " + body.getTable() + ";", Integer.class);
        } catch (Exception e) {
            LOGGER.error("exception is {}", e);
            return CommonReturn.create(ExceptionEnum.DATABASE_ERROR.getErrCode(), ExceptionEnum.DATABASE_ERROR.getErrMsg());
        }
        LOGGER.info("init count is {}", count);
        List<Map<String, Object>> columns = jdbcTemplate.queryForList(getColumnSql(body));
        int columnCount = columns.size();
        LOGGER.info("column count is {}", columnCount);
        String[] recordArray = body.getRecord().split(",");
        if (recordArray.length != columnCount) {
            return CommonReturn.create(ExceptionEnum.RECORD_ERROR.getErrCode(), ExceptionEnum.RECORD_ERROR.getErrMsg());
        }
        String sql = getSql(body);
        jdbcTemplate.execute(sql);
        int finishCount = jdbcTemplate.queryForObject("select count(*) from " + body.getTable() + ";", Integer.class);
        DatasourceManage.clear();
        return CommonReturn.create(true, "成功，初始" + count + "条，添加后总共" + finishCount + "条");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/columns")
    @ResponseBody
    public CommonReturn getColumns(@RequestBody Body body) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DatasourceManage.getDataSource(body));
        List<Map<String, Object>> columns = jdbcTemplate.queryForList(getColumnSql(body));
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> column : columns) {
            sb.append(column.get("column_name"));
            sb.append("(");
            sb.append(column.get("data_type"));
            sb.append(")");
            sb.append(", ");
        }
        String answer = sb.substring(0, sb.length() - 2);
        LOGGER.info("return columns is {}", columns);
        return CommonReturn.create(answer);
    }

    @RequestMapping(method = RequestMethod.GET, path = "loadParam")
    @ResponseBody
    public CommonReturn loadParam(@RequestParam String type) throws Exception {
        LOGGER.info("type is {}", type);
        String userDirPath = System.getProperty("user.dir");
        Body body = null;
        if (type.equals("MySQL")) {
            String path = userDirPath + File.separator + "config" + File.separator + "MySQL.properties";
            LOGGER.info("mysql config file path is {}", path);
            body = getBody(path, type);
        }
        if (type.equals("GaussDB")) {
            String path = userDirPath + File.separator + "config" + File.separator + "GaussDB.properties";
            LOGGER.info("gaussdb config file path is {}", path);
            body = getBody(path, type);
        }
        LOGGER.info("config file body is {}", body);
        return CommonReturn.create(body);
    }

    private Body getBody(String path, String type) throws IOException {
        try (InputStream is = new FileInputStream(path)) {
            Properties properties = new Properties();
            properties.load(is);
            Body body = Body.builder().username(properties.getProperty("username"))
                    .password(properties.getProperty("password"))
                    .ip(properties.getProperty("ip"))
                    .port(properties.getProperty("port"))
                    .database(properties.getProperty("database"))
                    .table(properties.getProperty("table"))
                    .count(StringUtils.isEmpty(properties.getProperty("count")) ? 0 : Integer.parseInt(properties.getProperty("count")))
                    .record(properties.getProperty("record"))
                    .type(type)
                    .build();
            return body;
        }
    }

    private String getSql(Body body) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(body.getTable());
        sb.append(" values ");
        for (int i = 0; i < body.getCount(); i++) {
            if (i == body.getCount() - 1) {
                sb.append("(");
                sb.append(getValue(body.getRecord()));
                sb.append(")");
                sb.append(";");
            } else {
                sb.append("(");
                sb.append(getValue(body.getRecord()));
                sb.append(")");
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private String getValue(String record) {
        String[] split = record.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            if (s.equals("null")) {
                sb.append(s);
                sb.append(",");
            } else {
                sb.append("'");
                sb.append(s);
                sb.append("'");
                sb.append(",");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private String getColumnSql(Body body) {
        if (body.getType().equals("MySQL")) {
            return String.format("SELECT * FROM information_schema.columns WHERE table_name = '%1$s' AND table_schema = '%2$s';", body.getTable(), body.getDatabase());
        }
        if (body.getType().equals("GaussDB")) {
            return "";
        }
        return null;
    }

}
