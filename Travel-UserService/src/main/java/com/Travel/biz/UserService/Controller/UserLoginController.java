package com.Travel.biz.UserService.Controller;

import com.Travel.biz.UserService.Service.Info.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserLoginController {
    @Autowired UserInfoService userInfoService;

    @ExceptionHandler(Exception.class)
    @GetMapping("/login")
    public String GetLoginPage(HttpServletRequest req) {
        return "login/creation";
    }

}
