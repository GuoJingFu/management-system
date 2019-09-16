package priv.linyu.manage.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;

/**
 * @className: DruidConfig
 * @author: QiuShangLin
 * @description: Druid数据源配置类
 * @date: 2019/7/18 0:55
 * @version: 1.0
 **/
@Configuration
public class DruidConfig {

    /**
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.druid")
    public DruidDataSource druid(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return dataSource;
    }

    /**
     *
     * @return
     */
    private Filter statFilter(){
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(5000);
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        return statFilter;
    }

    /**
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        Map<String,String> initParams = Maps.newHashMap();
        initParams.put(StatViewServlet.PARAM_NAME_USERNAME,"admin");
        initParams.put(StatViewServlet.PARAM_NAME_PASSWORD, "admin");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = Maps.newHashMap();
        initParams.put(WebStatFilter.PARAM_NAME_EXCLUSIONS,"*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        // 设置拦截
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}
