package com.yy.common;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * description
 *
 * @author yangzai
 * @data 2020/11/15
 */
public class DatasourceManage {

    private static ThreadLocal<DruidDataSource> threadLocal = new ThreadLocal<>();

    public static DataSource getDataSource() throws Exception {
        if (threadLocal.get() == null) {
            DruidDataSource dataSource = new DruidDataSource();
            ClassPathResource classPathResource = new ClassPathResource("druid.properties");
            File file = classPathResource.getFile();
            Properties properties = new Properties();
            try (InputStream is = new FileInputStream(file)) {
                properties.load(is);
                dataSource.configFromPropety(properties);
            }
            dataSource.configFromPropety(properties);
            dataSource.setBreakAfterAcquireFailure(true);
            dataSource.setNotFullTimeoutRetryCount(0);
            threadLocal.set(dataSource);
            return threadLocal.get();
        }
        return threadLocal.get();
    }
}
