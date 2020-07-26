package com.Travel.biz.MyPage.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @GetMapping("JwtTest")
    @ResponseBody
    public ResponseEntity<String> test(@RequestHeader HttpHeaders httpHeaders) {
        System.out.println("HEADER");
        System.out.println(httpHeaders);
        return ResponseEntity.ok("success");
    }
}
