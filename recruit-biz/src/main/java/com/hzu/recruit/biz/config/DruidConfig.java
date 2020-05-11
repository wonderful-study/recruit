package com.hzu.recruit.biz.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    //此注解将dataSource与application.properties相绑定
    @ConfigurationProperties(prefix="spring.druid")
    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource() {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));

        return dataSource;
    }

    @Bean
    public Filter statFilter() {

        StatFilter filter = new StatFilter();
        //指定日志的时间
        filter.setSlowSqlMillis(1);
        //约定是否打印出main日志
        filter.setLogSlowSql(true);
        //是否将日志合并
        filter.setMergeSql(true);

        return filter;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {

        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }
}
