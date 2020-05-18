package com.yiban.yblaas.shiro.filter;

import com.yiban.yblaas.session.CustomSessionManager;
import com.yiban.yblaas.session.RedisSessionDao;
import com.yiban.yblaas.shiro.authc.UserModularRealmAuthorizer;
import com.yiban.yblaas.shiro.authc.pam.UserModularRealmAuthenticator;
import com.yiban.yblaas.shiro.cache.ShiroCacheManager;
import com.yiban.yblaas.shiro.realm.RealmADMIN;
import com.yiban.yblaas.shiro.realm.RealmYIBAN;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: yblaas
 * @description: shiro过滤器
 * @author: xiaozhu
 * @create: 2020-03-13 21:10
 **/
@Configuration
public class ShiroConfiguration {

    @Resource
    private RealmADMIN realmADMIN;
    @Resource
    private RealmYIBAN realmYIBAN;
    @Resource
    private RedisSessionDao redisSessionDao;
    @Resource
    private ShiroCacheManager shiroCacheManager;

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url以及验证失败的url
        //loginUrl：没有登录的用户请求需要登录的页面时自动跳转到登录页面。
        bean.setLoginUrl("/public/user_login");
        //unauthorizedUrl：没有权限默认跳转的页面，登录的用户访问了没有被授权的资源自动跳转到的页面。
        bean.setUnauthorizedUrl("/error/403.html");
        //配置自定义的Filter
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("roles", new RolesOrFilter());
        bean.setFilters(filtersMap);
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "anon"); //首页
        filterChainDefinitionMap.put("/error/**", "anon"); //错误页
        filterChainDefinitionMap.put("/hui/**", "anon"); //hui模板
        filterChainDefinitionMap.put("/light7/**", "anon"); //light7框架
        filterChainDefinitionMap.put("/weui/**", "anon"); //weui框架
        filterChainDefinitionMap.put("/favicon.ico", "anon"); //图标
        filterChainDefinitionMap.put("/public/**", "anon"); //开放的controller
        filterChainDefinitionMap.put("/druid/**", "roles[admin]"); //druid 管理员才可进入
        filterChainDefinitionMap.put("/admin/**", "roles[admin]"); //管理员的页面
        filterChainDefinitionMap.put("/teacher/**", "roles[teacher,fdy,xyld,xgc]"); //老师的页面
        filterChainDefinitionMap.put("/student/**", "roles[student]"); //学生的页面
        filterChainDefinitionMap.put("/**","authc"); //需要登陆授权
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>();
        //添加多个Realm
        realms.add(realmADMIN);
        //admin需要加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(); //创建加密对象
        matcher.setHashAlgorithmName("md5"); //加密的算法
        matcher.setHashIterations(1);//加密次数
        realmADMIN.setCredentialsMatcher(matcher); //放入自定义Realm
        realms.add(realmYIBAN);
        manager.setAuthenticator(modularRealmAuthenticator());  // 需要再realm定义之前 放入自定义的多Realm认证
        manager.setAuthorizer(modularRealmAuthorizer()); //放入自定义的多Realm授权
        manager.setRealms(realms);
        //配置自定义sessionManager
        manager.setSessionManager(sessionManager());
        //配置自定义cacheManager
        manager.setCacheManager(shiroCacheManager);
        return manager;
    }

    /**
     * 功能描述:
     * (系统自带的Realm管理，主要针对多realm 认证)
     *
     * @return : ModularRealmAuthenticator
     * @author : xiaozhu
     * @date : 2020/3/13 21:13
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * 功能描述:
     * (系统自带的Realm管理，主要针对多realm 授权)
     *
     * @return : org.apache.shiro.authz.ModularRealmAuthorizer
     * @author : xiaozhu
     * @date : 2020/3/13 22:03
     */
    @Bean
    public ModularRealmAuthorizer modularRealmAuthorizer() {
        //自己重写的ModularRealmAuthorizer
        UserModularRealmAuthorizer modularRealmAuthorizer = new UserModularRealmAuthorizer();
        return modularRealmAuthorizer;
    }

    /**
     * 功能描述:
     * (开启注解支持)
     *
     * @param securityManager 1
     * @return : AuthorizationAttributeSourceAdvisor
     * @author : xiaozhu
     * @date : 2020/3/13 22:06
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 功能描述:
     * (自定义session缓存)
     *
     * @return : com.yiban.yblaas.session.CustomSessionManager
     * @author : xiaozhu
     * @date : 2020/4/17 11:19
     */
    @Bean
    public CustomSessionManager sessionManager(){
        //把sessionManager注入Bean
        CustomSessionManager manager = new CustomSessionManager();
        manager.setSessionDAO(redisSessionDao);
        return manager;
    }
}
