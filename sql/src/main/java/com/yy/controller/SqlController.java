package com.yy.controller;

import com.yy.common.DatasourceManage;
import com.yy.exception.ExceptionEnum;
import com.yy.model.Body;
import com.yy.model.CommonReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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
        if (body.getCount() >= 100000) {
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
        DatasourceManage.clear();
        return CommonReturn.create(true, "finish...");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/columns")
    @ResponseBody
    public CommonReturn getColumns(@RequestBody Body body) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DatasourceManage.getDataSource(body));
        List<Map<String, Object>> columns = jdbcTemplate.queryForList(getColumnSql(body));
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> column : columns) {
            sb.append(column.get("column_name"));
            sb.append(",");
        }
        String answer = sb.substring(0, sb.length() - 1);
        return CommonReturn.create(answer);
    }

    private String getSql(@RequestBody Body body) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(body.getTable());
        sb.append(" values ");
        for (int i = 0; i < body.getCount(); i++) {
            if (i == body.getCount() - 1) {
                sb.append("(");
                sb.append(body.getRecord());
                sb.append(")");
                sb.append(";");
            } else {
                sb.append("(");
                sb.append(body.getRecord());
                sb.append(")");
                sb.append(",");
            }
        }
        return sb.toString();
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
