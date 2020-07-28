package com.Travel.biz.MyPage.Controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @ApiOperation(value = "/JwtTest", notes = "Json Web Token 테스트 url")
    @GetMapping("/JwtTest")
    @ResponseBody
    public ResponseEntity<String> test(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.ok("success");
    }
}
