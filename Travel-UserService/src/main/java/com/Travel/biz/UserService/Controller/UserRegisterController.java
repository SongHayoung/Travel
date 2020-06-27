package com.Travel.biz.UserService.Controller;

import com.Travel.Validation.EmailUnique;
import com.Travel.Validation.IDUnique;
import com.Travel.biz.UserService.Service.Register.DuplicateUserEmailException;
import com.Travel.biz.UserService.Service.Register.DuplicateUserIDException;
import com.Travel.biz.UserService.Service.Register.UserRegisterService;
import com.Travel.biz.User.VO.UserVO;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Locale;

// url : /user/
@RestController
public class UserRegisterController {
    @Autowired UserRegisterService userRegisterService;
    @Autowired MessageSource messageSource;
    @Autowired LocaleResolver localeResolver;
    private Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

    @GetMapping("/register")
    public String GetLoginPage(HttpServletRequest req) {
        return "login/creation";
    }

    @PostMapping("/register/validEmail")
    @ResponseBody
    public RegisterMail validateEmail(@Valid @RequestBody@Email @EmailUnique @NotNull String email, Locale locale) {
        String authNum = authNumGenerator();
        userRegisterService.sendRegisterMail(email, authNum);
        Object params[] = {email};
        return RegisterMail.builder().
                message(messageSource.getMessage("userService.registerMailSend", params,locale).toString()).
                authNum(authNum).
                build();
    }

    @PostMapping("/register/validID")
    @ResponseBody
    public String validateID(@Valid @RequestBody @IDUnique @NotNull String id, Locale locale) {
        Object params[] = {id};
        return messageSource.getMessage("userService.availableID", params,locale);
    }

    //등록이 성공하면 로케일 메세지를 담은 플래시 애트리뷰트를 담아 리다이렉트 시킨
    @PostMapping("/register/addUser")
    @ResponseBody
    public String addUser(@Valid @RequestBody UserVO user, Locale locale, RedirectAttributes attributes) {
        userRegisterService.addUser(user);

        Object params[] = null;
        attributes.addFlashAttribute("message",messageSource.getMessage("userService.addUser",params,locale));

        return "redirect:/login";
    }

    private String authNumGenerator() {
        StringBuffer buffer = new StringBuffer(6);
        for (int i = 0; i < 6; i++) {
            buffer.append((int) (Math.random() * 10));
        }
        return buffer.toString();
    }

    //외부 참조로 인한 memory leak 방지를 위해 static
    @Data
    @Builder
    static class RegisterMail {
        String authNum;
        String message;
    }

    @ExceptionHandler(DuplicateUserEmailException.class)
    @ResponseBody
    public ResponseEntity<String> duplicateUserEmailError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};
        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.duplicatedEmail",rejectvalue ,locale));
    }

    @ExceptionHandler(DuplicateUserIDException.class)
    @ResponseBody
    public ResponseEntity<String> duplicateUserIDError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};
        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.duplicatedID",rejectvalue ,locale));
    }
}
