package com.Travel.biz.User.Controller;

import com.Travel.Validation.InCorrectID;
import com.Travel.Validation.InCorrectPassword;
import com.Travel.biz.User.Service.Info.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserLoginController {
    @Autowired UserInfoService userInfoService;
    @Autowired MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @GetMapping("/login")
    public String GetLoginPage(HttpServletRequest req) {
        return "login/creation";
    }

    @PostMapping("/login")
    public String PostLoginPage(@RequestBody @InCorrectID String id, @RequestBody @InCorrectPassword String pass) {
        userInfoService.loginUser(id, pass);

        return "redirect:/main";
    }
}
