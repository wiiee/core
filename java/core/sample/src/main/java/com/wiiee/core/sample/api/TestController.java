package com.wiiee.core.sample.api;

import com.wiiee.core.domain.service.ServiceResult;
import com.wiiee.core.sample.domain.entity.User;
import com.wiiee.core.sample.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private static final Logger _logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getItems() {
        try{
            userService.create(new User(null, "123", "bill"));
            ServiceResult result = userService.get();
            return result.datum;
        }
        catch (Exception ex){
            _logger.error(ex.getMessage());
            return new ArrayList<>();
        }
    }
}