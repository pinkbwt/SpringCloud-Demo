package org.lion.microservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
@Schema(title = "测试服务控制器")
public class ServiceController {

    @Operation(summary="测试服务的hello接口，返回hello + 输入name参数")
    @GetMapping(value = "/server/hello")
    public String hello(String name) {
        log.info("server called at {}",new Date());
        return String.format("Hello %s",name);
    }
}
