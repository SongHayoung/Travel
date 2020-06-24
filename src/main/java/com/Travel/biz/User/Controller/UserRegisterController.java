package com.Travel.biz.User.Controller;

import com.Travel.Validation.RegisterRequest;
import com.Travel.biz.User.Service.Register.DuplicateUserEmailException;
import com.Travel.biz.User.Service.Register.DuplicateUserIdException;
import com.Travel.biz.User.Service.Register.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserRegisterController {
    @Autowired UserRegisterService userRegisterService;

    @GetMapping("/register")
    public String GetLoginPage(HttpServletRequest req) {
        return "login/creation";
    }

    @PostMapping("/register")
    ResponseEntity<String> validateBody(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok("valid");
    }

    @ExceptionHandler(DuplicateUserIdException.class)
    public Object DuplicateUserIdException(Exception e) { return e.getMessage(); }


    @ExceptionHandler(DuplicateUserEmailException.class)
    public Object DuplicateUserEmailException(Exception e) { return e.getMessage(); }
}
