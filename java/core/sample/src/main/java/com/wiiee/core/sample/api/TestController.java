package com.wiiee.core.sample.api;

import com.wiiee.core.sample.domain.entity.User;
import com.wiiee.core.sample.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private static final Logger _logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getItems() {
        return userService.get().datum;
    }

    @PostMapping
    public String hello(@RequestBody String name){
        return "hello, world" + name;
    }
}