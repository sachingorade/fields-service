package com.test.fields.agromonitoring;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppIdInterceptor implements RequestInterceptor {

    @Value("${agromonitoring.api.appId}")
    private String appId;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        StringBuilder valueToAppend = new StringBuilder();
        if (!requestTemplate.queryLine().contains("?")) {
            valueToAppend.append("?").append("appid=").append(appId);
            requestTemplate.uri(valueToAppend.toString(), true);
        } else {
            valueToAppend.append(requestTemplate.queryLine()).append("&").append("appid=").append(appId);
            requestTemplate.uri(valueToAppend.toString());
        }
    }

}
