package com.yy.common;

import com.alibaba.druid.pool.DruidDataSource;
import com.yy.model.Body;
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

    public static DataSource getDataSource(Body body) throws Exception {
        if (threadLocal.get() == null) {
            DruidDataSource dataSource = new DruidDataSource();
            ClassPathResource classPathResource = new ClassPathResource("druid.properties");
            File file = classPathResource.getFile();
            Properties properties = new Properties();
            try (InputStream is = new FileInputStream(file)) {
                properties.load(is);
                dataSource.configFromPropety(properties);
            }
            dataSource.setUsername(body.getUsername());
            dataSource.setPassword(body.getPassword());
            dataSource.setUrl(getUrl(body));
            dataSource.setDriverClassName(getDriver(body));
            dataSource.configFromPropety(properties);
            dataSource.setBreakAfterAcquireFailure(true);
            dataSource.setNotFullTimeoutRetryCount(0);
            threadLocal.set(dataSource);
            return threadLocal.get();
        }
        return threadLocal.get();
    }

    private static String getDriver(Body body) {
        if (body.getType().equals("MySQL")) {
            return "com.mysql.jdbc.Driver";
        }
        return null;
    }

    private static String getUrl(Body body) {
        if (body.getType().equals("MySQL")) {
            return String.format("jdbc:mysql://%1$s:%2$s/%3$s?serverTimezone=UTC&useUnicode=true", body.getIp(), body.getPort(), body.getDatabase());
        }
        return null;
    }

    public static void clear() {
        if (threadLocal.get() != null) {
            threadLocal.get().close();
        }
    }
}
