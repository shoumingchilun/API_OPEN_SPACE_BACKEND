package com.chilun.apiopenspace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 齿轮
 * @date 2023-12-21-0:01
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/test1")
    public String test1(){
        return "hello API_OPEN_SPACE";
    }
}
