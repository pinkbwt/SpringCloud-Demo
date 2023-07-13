package com.lion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    public static final long DEFAULT_TIMEOUT = 30000;
    public static String NACOS_SERVER_ADDR;
    public static String NACOS_NAMESPACE;
    public static String NACOS_ROUTE_DATA_ID;
    public static String NACOS_ROUTE_GROUP;
    public static String NACOS_USERNAME;
    public static String NACOS_PASSWORD;

    @Value("${spring.cloud.nacos.config.server-addr}")
    public void setNacosServerAddr(String nacosServerAddr) {
        NACOS_SERVER_ADDR = nacosServerAddr;
    }

    @Value("${spring.cloud.nacos.config.namespace}")
    public void setNacosNamespace(String nacosNamespace) {
        NACOS_NAMESPACE = nacosNamespace;
    }

    @Value("${nacos.gateway.route.config.data-id}")
    public void setNacosRouteDataId(String nacosRouteDataId) {
        NACOS_ROUTE_DATA_ID = nacosRouteDataId;
    }

    @Value("${spring.cloud.nacos.config.group}")
    public void setNacosRouteGroup(String nacosRouteGroup) {
        NACOS_ROUTE_GROUP = nacosRouteGroup;
    }

    @Value("${spring.cloud.nacos.config.username}")
    public void setNacosUsername(String nacosUsername) {
        NACOS_USERNAME = nacosUsername;
    }

    @Value("${spring.cloud.nacos.config.password}")
    public void setNacosPassword(String nacosPassword) {
        NACOS_PASSWORD = nacosPassword;
    }
}
