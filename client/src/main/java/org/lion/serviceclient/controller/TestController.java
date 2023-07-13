package org.lion.serviceclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.lion.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    private ToDoService toDoService;

    @Autowired
    public void setToDoService(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/test")
    public String test(){
        String test=toDoService.hello("sfasf");
        log.info("The result is {}",test);
        return String.format("name: %s",test);
    }
}
