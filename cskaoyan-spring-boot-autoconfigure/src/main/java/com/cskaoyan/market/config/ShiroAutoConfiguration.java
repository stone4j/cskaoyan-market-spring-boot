package com.cskaoyan.market.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
@ConditionalOnProperty("cskaoyan.shiro.enabled")
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration implements ApplicationContextAware {

    ShiroProperties shiroProperties;
    List<Realm> realms;
    ApplicationContext applicationContext;

    public ShiroAutoConfiguration(ShiroProperties shiroProperties) {
        this.shiroProperties = shiroProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setLoginUrl(shiroProperties.getRedirectUrl());
        filter.setSecurityManager(securityManager);
        LinkedHashMap<String, String> filterChain = shiroProperties.getFilterChain();
        filter.setFilterChainDefinitionMap(filterChain);
        return filter;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultWebSecurityManager securityManager(DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        if (realms != null && realms.size() > 0) {
            securityManager.setRealms(realms);
        }
        return securityManager;
    }

    @Bean
    @ConditionalOnMissingBean
    //@ConditionalOnProperty({"cskaoyan.shiro.token-header.wx","cskaoyan.shiro.token-header.admin"})
    public DefaultWebSessionManager sessionManager() {
        return new MarketSessionManager(shiroProperties.tokenHeader.getAdmin(),shiroProperties.tokenHeader.wx);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @PostConstruct
    public void setRealms(){
        Map<String, Realm> beansOfType = applicationContext.getBeansOfType(Realm.class);
        if (beansOfType != null) {
            realms = new ArrayList<>(beansOfType.values());
        }
    }
}
