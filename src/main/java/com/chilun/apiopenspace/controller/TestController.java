package com.chilun.apiopenspace.controller;

import com.chilun.apiopenspace.model.Masked.UserMasked;
import com.chilun.apiopenspace.model.entity.User;
import com.chilun.apiopenspace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 齿轮
 * @date 2023-12-21-0:01
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/test1")
    public String test1() {
        return "hello API_OPEN_SPACE";
    }

    @Autowired
    UserService userService;

    @GetMapping("/user/register")
    public String register(@RequestParam("account") String username,
                           @RequestParam("password") String password,
                           @RequestParam("checkedPassword") String checkedPassword) {
        return String.valueOf(userService.userRegister(username, password, checkedPassword));
    }

    @GetMapping("/user/login")
    public String login(@RequestParam("account") String username,
                            @RequestParam("password") String password,
                            HttpServletRequest request) {
        String ret = "";
        UserMasked userMasked = userService.userLogin(username, password, request);
        ret+="userMasked->"+userMasked.toString();
        User loggedInUser = userService.getLoggedInUser(request);
        ret+="\tuser->"+loggedInUser.toString();
        boolean isAdmin = userService.isAdmin(request);
        ret+="\tisAdminByRequest->"+isAdmin;
        isAdmin = userService.isAdmin(loggedInUser);
        ret+="\tisAdminByUser->"+isAdmin;
        userService.userLogout(request);
        User loginUserPermitNull = userService.getLoggedInUserPermitNull(request);
        ret+="\tLogoutUser->"+loginUserPermitNull;
        return ret;
    }
}
