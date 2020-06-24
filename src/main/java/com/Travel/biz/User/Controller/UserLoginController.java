package com.Travel.biz.User.Controller;

import com.Travel.biz.User.Service.Info.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserLoginController {
    @Autowired UserInfoService userInfoService;

    @ExceptionHandler(Exception.class)
    @GetMapping("/login")
    public String GetLoginPage(HttpServletRequest req) {
        return "login/creation";
    }

    @ExceptionHandler(Exception.class)
    @PostMapping(value = "/login")
    public String Submit(@RequestParam("id") String id, @RequestParam("pass") String pass, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("pass", pass);
        return "main";
    }
}
