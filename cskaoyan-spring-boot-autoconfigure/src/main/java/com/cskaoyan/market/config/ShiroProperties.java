package com.cskaoyan.market.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.List;

@ConfigurationProperties(prefix = "cskaoyan.shiro")
public class ShiroProperties implements InitializingBean {

    LinkedHashMap<String,String> filterChain = new LinkedHashMap<>();
    LinkedHashMap<String,String> defaultfilterChain = new LinkedHashMap<>();
    //List realms;
    String redirectUrl = "/admin/auth/401";
    TokenHeader tokenHeader;
    {
        defaultfilterChain.put("/admin/auth/login", "anon");
        defaultfilterChain.put("/admin/auth/info", "anon");
        defaultfilterChain.put("/admin/auth/401", "anon");
        defaultfilterChain.put("/admin/**", "authc");
    }


    public LinkedHashMap<String, String> getFilterChain() {
        return filterChain;
    }

    public void setFilterChain(LinkedHashMap<String, String> filterChain) {
        this.filterChain = filterChain;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public TokenHeader getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(TokenHeader tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public ShiroProperties() {
        this.tokenHeader = new ShiroProperties.TokenHeader();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (filterChain.values().size() == 0) {
            this.filterChain = defaultfilterChain;
        } else {
            this.filterChain = filterChain;
        }
    }

    /*public List getRealms() {
        return realms;
    }

    public void setRealms(List realms) {
        this.realms = realms;
    }*/

    public static class TokenHeader{
        String admin = "X-CskaoyanMarket-Admin-Token";
        String wx = "X-CskaoyanMarket-Token";

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public String getWx() {
            return wx;
        }

        public void setWx(String wx) {
            this.wx = wx;
        }
    }
}
