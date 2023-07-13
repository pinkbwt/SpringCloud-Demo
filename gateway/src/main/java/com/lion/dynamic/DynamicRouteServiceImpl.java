package com.lion.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    @Autowired
    public void setRouteDefinitionWriter(RouteDefinitionWriter routeDefinitionWriter) {
        this.routeDefinitionWriter = routeDefinitionWriter;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    public String delete(String id){
        try {
            log.info("gateway delete route id {}",id);
            this.routeDefinitionWriter.delete(Mono.just(id));
            return "delete success";
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return "delete fail";
        }
    }


    public String update(RouteDefinition definition){
        try {
            log.info("gateway update route {}",definition);
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return String.format("%s %s", "update fail,not find route routeId:",definition.getId());
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "update gateway success";
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return "update route fail";
        }
    }

    public String add(RouteDefinition definition){
        log.info("gateway add route {}",definition);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

}
