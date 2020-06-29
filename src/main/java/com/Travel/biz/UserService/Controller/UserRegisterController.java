package com.Travel.biz.UserService.Controller;

import com.Travel.biz.UserService.Dto.UserIdDto;
import com.Travel.biz.UserService.Service.Register.DuplicateUserEmailException;
import com.Travel.biz.UserService.Service.Register.DuplicateUserIDException;
import com.Travel.biz.UserService.Service.Register.UserRegisterService;
import com.Travel.Core.User.VO.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
public class UserRegisterController {
    @Autowired UserRegisterService userRegisterService;
    @Autowired MessageSource messageSource;
    @Autowired LocaleResolver localeResolver;
    private Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

    @PostMapping("/register/valid/email")
    public ResponseEntity<RegisterMailDto> validateEmail(@Valid @RequestBody RegisterMailDto mail, Locale locale) {
        String authNum = authNumGenerator();
        userRegisterService.isDuplicateEmail(mail.getEmail());
        userRegisterService.sendRegisterMail(mail.getEmail(), authNum);

        Object params[] = {mail.getEmail()};
        return ResponseEntity.ok(
                RegisterMailDto.builder().
                email(mail.getEmail()).
                message(messageSource.getMessage("userService.registerMailSend", params, locale)).
                authNum(authNum).
                build());
    }

    @PostMapping("/register/valid/id")
    public ResponseEntity<String> validateID(@Valid @RequestBody UserIdDto user, Locale locale) {
        userRegisterService.isDuplicateId(user);

        Object params[] = {user.getId()};
        return ResponseEntity.ok(messageSource.getMessage("userService.availableID", params,locale));
    }

    //등록이 성공하면 로케일 메세지를 담은 플래시 애트리뷰트를 담아 리다이렉트 시킨
    @PostMapping("/register/user")
    public String addUser(@Valid @RequestBody UserVO user, Locale locale, RedirectAttributes attributes) {
        userRegisterService.addUser(user);

        attributes.addFlashAttribute("message",messageSource.getMessage("userService.addUser", null, locale));

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
    public ResponseEntity<String> duplicateUserEmailError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};
        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.duplicatedEmail",rejectvalue ,locale));
    }

    @ExceptionHandler(DuplicateUserIDException.class)
    public ResponseEntity<String> duplicateUserIDError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};
        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.duplicatedID",rejectvalue ,locale));
    }
}
