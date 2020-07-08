package com.star.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //shirofilterFactoryBean:第三步
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("webSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro内置过滤器 anno：无需认证就可访问 authc：必须认证了才能访问  perms：拥有对某个资源的权限才能访问 role：拥有某个角色权限才能访问
        Map<String,String> filterMap= new LinkedHashMap<>();

        filterMap.put("admin/*","authc");
//        filterMap.put("/test/add","perms[user:add]");//授权，正常情况下，未授权的用户会跳转到未授权界面
//        filterMap.put("/test/*","authc");

        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/admin");//设置登陆的请求
//        bean.setUnauthorizedUrl("/unauthorized");
        return bean;
    }

    //DefaultWebSecuritymanager：第二步
    @Bean(name ="webSecurityManager" )
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
            DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
            //关联realm
            defaultWebSecurityManager.setRealm(userRealm);
            return defaultWebSecurityManager;
    };
    //创建realm对象:第一步
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
    //整合ShiroDialect;整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
