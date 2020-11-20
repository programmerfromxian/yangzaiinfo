package com.yy.controller;

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

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author yangzai
 * @data 2020/11/20
 */
@Controller
@RequestMapping("/insert")
public class SqlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlController.class);

    @RequestMapping(method = RequestMethod.POST)
    public CommonReturn insert(@RequestBody Body body) {
        if (body.getCount() <= 0) {
            return CommonReturn.create(false, "输入条数");
        }
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        int count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count * from " + body.getTable() + ";", Integer.class);
        } catch (Exception e) {
            LOGGER.error("exception is {}", e);
            return CommonReturn.create(false, "检查输入的数据库信息");
        }
        LOGGER.info("init count is {}", count);
        List<Map<String, Object>> columns = jdbcTemplate.queryForList("");
        int columnCount = columns.get(0).size();
        LOGGER.info("column count is {}", columnCount);
        String[] recordArray = body.getRecord().split(",");
        if (recordArray.length != columnCount) {
            return CommonReturn.create(false, "核对输入的列");
        }
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
        jdbcTemplate.execute(sb.toString());
        return CommonReturn.create(true, "finish...");
    }
}
