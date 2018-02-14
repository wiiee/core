package com.wiiee.core.sample.api;

import com.wiiee.core.domain.service.ServiceResult;
import com.wiiee.core.sample.domain.entity.User;
import com.wiiee.core.sample.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger _logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @PostMapping("/logIn")
    public ServiceResult<User> logIn() {
        return ServiceResult.SUCCESS;
    }

    @PostMapping("/signUp")
    public ServiceResult<User> signUp(@RequestBody User user) {
        ServiceResult<User> result = userService.signUp(user);

//        if (result.isSuccessful) {
//            String username = user.getId();
//            String password = user.password;
//
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//
//            // generate session if one doesn't exist
//            request.getSession();
//
//            token.setDetails(new WebAuthenticationDetails(request));
//            Authentication authenticatedUser = authenticationManager.authenticate(token);
//
//            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//        }

        return result;
    }

    @GetMapping
    public ServiceResult<User> get() {
        return userService.get();
    }
}
