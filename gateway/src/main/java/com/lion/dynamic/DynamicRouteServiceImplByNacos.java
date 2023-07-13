package com.lion.dynamic;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.lion.config.GatewayConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

@Component
@Slf4j
@DependsOn({"gatewayConfig"})
public class DynamicRouteServiceImplByNacos {

    private DynamicRouteServiceImpl dynamicRouteService;

    @Autowired
    public void setDynamicRouteService(DynamicRouteServiceImpl dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    private ConfigService configService;

    @PostConstruct
    public void init() {
        log.info("gateway route init ...");
        try {
            configService = initConfigService();
            if (null==configService){
                log.warn("Init config service fail");
            }
            String configInfo=configService.getConfig(GatewayConfig.NACOS_ROUTE_DATA_ID,GatewayConfig.NACOS_ROUTE_GROUP,
                    GatewayConfig.DEFAULT_TIMEOUT);
            log.info("获取当前网关配置：\r\n{}",configInfo);
            List<RouteDefinition> definitions = JSON.parseArray(configInfo,RouteDefinition.class);
            for (RouteDefinition definition:definitions){
                log.info("update route: {}",definition.toString());
                dynamicRouteService.add(definition);
            }
        }catch (Exception e){
            log.error("初始化网关路由时发生错误",e);
        }
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTE_DATA_ID,GatewayConfig.NACOS_ROUTE_GROUP);
    }

    public void dynamicRouteByNacosListener(String dateId,String group){
        try {
            configService.addListener(dateId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    log.info("getExecutor\n\r");
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("进行网关路由更新:\n\r{}",configInfo);
                    List<RouteDefinition> definitions = JSON.parseArray(configInfo,RouteDefinition.class);
                    for (RouteDefinition definition:definitions){
                        log.info("update route: {}",definition.toString());
                        dynamicRouteService.update(definition);
                    }
                }
            });
        }catch (Exception e){
            log.error("从Nacos接收动态路由配置出错！！",e);
        }
    }


    private ConfigService initConfigService() {
        try {
            Properties properties=new Properties();
            properties.setProperty("serverAddr",GatewayConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace",GatewayConfig.NACOS_NAMESPACE);
            properties.setProperty("username",GatewayConfig.NACOS_USERNAME);
            properties.setProperty("password",GatewayConfig.NACOS_PASSWORD);
            return configService= NacosFactory.createConfigService(properties);
        }catch (Exception e) {
            log.error("初始化config service时出错",e);
            return null;
        }
    }
}
