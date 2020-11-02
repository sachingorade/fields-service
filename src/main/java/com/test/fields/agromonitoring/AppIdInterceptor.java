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
        if (!requestTemplate.url().contains("?")) {
            valueToAppend.append("?");
        }
        valueToAppend.append("appid=").append(appId);
        requestTemplate.uri(valueToAppend.toString(), true);
    }

}
