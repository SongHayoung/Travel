package com.Travel.biz.UserService.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @GetMapping("JwtTest")
    @ResponseBody
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("success");
    }
}
