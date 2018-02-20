package com.wiiee.core.platform.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties("app")
public class AppProperties {
    private String authenticationUrl;

    public String getAuthenticationUrl() {
        if(StringUtils.isEmpty(authenticationUrl)){
            return "/login";
        }

        return authenticationUrl;
    }

    public void setAuthenticationUrl(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }
}
