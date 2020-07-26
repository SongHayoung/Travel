package com.Travel.biz.MyPage.Controller;

import com.Travel.Core.Annotations.TODO;
import com.Travel.Validation.InCorrectID;
import com.Travel.biz.MyPage.Service.Register.DuplicateUserInfoException;
import com.Travel.biz.MyPage.Service.Register.UserRegisterService;
import com.Travel.Core.User.VO.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Locale;

@TODO("유저 가입 실패 에러 처리 필요 / authGenerator 어디다 둬야할까")
@RestController
public class UserRegisterController {
    @Autowired UserRegisterService userRegisterService;
    @Autowired MessageSource messageSource;
    @Autowired LocaleResolver localeResolver;
    private Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

    @PostMapping("/valid/email")
    public ResponseEntity<RegisterMailDto> validateEmail(@Valid @RequestBody RegisterMailDto mail, Locale locale) {
        try {
            userRegisterService.isDuplicateEmail(mail.getEmail());
        }
        catch (DuplicateUserInfoException e) {
            throw new DuplicateUserEmailException(e);
        }
        String authNum = authNumGenerator();
        userRegisterService.sendRegisterMail(mail.getEmail(), authNum);

        Object params[] = {mail.getEmail()};
        return ResponseEntity.ok(
                RegisterMailDto.builder().
                email(mail.getEmail()).
                message(messageSource.getMessage("userService.registerMailSend", params, locale)).
                authNum(authNum).
                build());
    }

    @PostMapping("/register/valid/id/{userId}")
    public ResponseEntity<String> validateID(@PathVariable("userId") @Valid @RequestBody @InCorrectID String userId, Locale locale) {
        try {
            userRegisterService.isDuplicateId(userId);
        }
        catch (DuplicateUserInfoException e) {
            throw new DuplicateUserIDException(userId);
        }
        Object params[] = {userId};
        return ResponseEntity.ok(messageSource.getMessage("userService.availableID", params,locale));
    }

    //등록이 성공하면 로케일 메세지를 담은 플래시 애트리뷰트를 담아 리다이렉트 시킨다
    @PostMapping("/register/user")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserVO user, Locale locale, RedirectAttributes attributes) {
        user.setRoles(Collections.singletonList("ROLE_USER"));
        userRegisterService.addUser(user);

        attributes.addFlashAttribute("message",messageSource.getMessage("userService.addUser", null, locale));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/users/login");
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

    private String authNumGenerator() {
        StringBuffer buffer = new StringBuffer(6);
        for (int i = 0; i < 6; i++) {
            buffer.append((int) (Math.random() * 10));
        }
        return buffer.toString();
    }

    //외부 참조로 인한 memory leak 방지를 위해 static
    //단순히 authNum과 message를 보내는 용도로만 사용
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class RegisterMailDto {
        @Email
        @NotNull
        @NotBlank
        String email;

        String authNum;

        String message;
    }

    @ExceptionHandler(DuplicateUserEmailException.class)
    @ResponseBody
    public ResponseEntity<String> duplicateUserEmailError(DuplicateUserEmailException ex, Locale locale) {
        Object rejectvalue[] = {ex.getMessage()};

        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.duplicatedEmail",rejectvalue ,locale));
    }

    @ExceptionHandler(DuplicateUserIDException.class)
    @ResponseBody
    public ResponseEntity<String> duplicateUserIDError(DuplicateUserIDException ex, Locale locale) {
        Object rejectvalue[] = {ex.getMessage()};

        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.duplicatedID",rejectvalue ,locale));
    }
}
