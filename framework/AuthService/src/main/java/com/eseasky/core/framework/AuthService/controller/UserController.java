package com.eseasky.core.framework.AuthService.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;

/**
*  用户信息控制器
*/
@Api(value = "用户管理",tags = "用户管理服务")
@RestController
@Log4j2
public class UserController {

    /**
     * 获取授权用户的信息
     * @param user 当前用户
     * @return 授权信息
     */
    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }

}
