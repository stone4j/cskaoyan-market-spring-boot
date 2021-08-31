package com.cskaoyan.market.config;

import com.cskaoyan.market.util.StringUtils;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;


public class MarketSessionManager extends DefaultWebSessionManager {

    String adminHeader;
    String wxHeader;

    public MarketSessionManager(String adminHeader, String wxHeader) {
        this.adminHeader = adminHeader;
        this.wxHeader = wxHeader;
    }

    public MarketSessionManager() {
    }

    public String getAdminHeader() {
        return adminHeader;
    }

    public void setAdminHeader(String adminHeader) {
        this.adminHeader = adminHeader;
    }

    public String getWxHeader() {
        return wxHeader;
    }

    public void setWxHeader(String wxHeader) {
        this.wxHeader = wxHeader;
    }

    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse response) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!StringUtils.isEmpty(adminHeader)) {
            String adminHeaderValue = request.getHeader(adminHeader);
            if (!StringUtils.isEmpty(adminHeaderValue)) {
                return adminHeaderValue;
            }
        }
        if (!StringUtils.isEmpty(wxHeader)) {
            String wxHeaderValue = request.getHeader(wxHeader);
            if (!StringUtils.isEmpty(wxHeaderValue)) {
                return wxHeaderValue;
            }
        }
        return super.getSessionId(request, response);
    }
}
