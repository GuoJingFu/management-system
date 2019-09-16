package priv.linyu.manage.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.linyu.manage.pojo.Resource;
import priv.linyu.manage.realm.AuthRealm;
import priv.linyu.manage.service.IResourceService;

import java.util.List;
import java.util.Map;

/**
 * @className: ShiroConfig
 * @author: QiuShangLin
 * @description: Shiro配置类
 * @date: 2019/7/19 0:05
 * @version: 1.0
 **/
@Configuration
@Slf4j
public class ShiroConfig {

    @Autowired
    private IResourceService resourceService;

    /**
     * shiro过滤器
     * @param authRealm
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("authRealm")AuthRealm authRealm){

        // 创建 shiro 工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置shiro安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager(authRealm));
        // 设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/admin/login.html");
        // 设置登录成功跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/401");

        // 设置访问权限
        Map<String,String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/templates/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/error/**", "anon");
        filterChainDefinitionMap.put("/easyui/**", "anon");
        filterChainDefinitionMap.put("/echarts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/login.do", "anon");
        filterChainDefinitionMap.put("/logout","logout");

        // 获取所有资源，并配置需要进行授权过滤的资源
        List<Resource> resourceList = resourceService.findAll();
        resourceList.forEach(resource -> {
            if (StringUtils.isNotBlank(resource.getResourceurl())){
                filterChainDefinitionMap.put(resource.getResourceurl(),"perms[" + resource.getResourceurl() + "]");
            }
        });


        // 过滤链定义，从上向下顺序执行，一般将/*放在最下边
        filterChainDefinitionMap.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return  shiroFilterFactoryBean;
    }

    /**
     * 配置shiro安全管理器
     * @param authRealm
     * @return
     */
    public SecurityManager securityManager(@Qualifier("authRealm")AuthRealm authRealm){
        log.info("- - - - - - -shiro开始加载- - - - - - ");
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 设置realm
        defaultWebSecurityManager.setRealm(authRealm);
        // 设置记住我
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        // 设置session管理器
        defaultWebSecurityManager.setSessionManager(webSessionManager());
        // 设置cache缓存

        return defaultWebSecurityManager;
    }

    /**
     * 记住我
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(rememberMeCookie());
        rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return rememberMeManager;
    }

    /**
     * cookie过期时间
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604800);
        return  cookie;
    }

    /**
     * 配置session管理器
     * @return
     */
    @Bean
    public SessionManager webSessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setGlobalSessionTimeout(60 * 60 * 1000);
        manager.setSessionValidationSchedulerEnabled(true);
        return  manager;
    }

    /**
     * AOP式方法级权限检查
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }


    /**
     *
     * @param authRealm
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("authRealm")AuthRealm authRealm){
        SecurityManager manager = securityManager(authRealm);
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }


    /**
     * 配置ShiroDialect后，可以在页面使用Shiro标签
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


    /**
     *
     * @return
     */
    @Bean
    public AuthRealm authRealm(){
        return new AuthRealm();
    }


}
