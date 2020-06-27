package com.Travel.biz.UserService.Controller;

import com.Travel.biz.UserService.Service.Info.PasswordValidationFailureException;
import com.Travel.biz.UserService.Service.Info.PasswordValueNotChangedException;
import com.Travel.biz.UserService.Service.Info.UserInfoService;
import com.Travel.biz.UserService.VO.UserNewNicknameVO;
import com.Travel.biz.UserService.VO.UserNewPasswordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.validation.Valid;
import java.util.Locale;

@RestController
public class UserServiceController {
    @Autowired UserInfoService userInfoService;
    @Autowired MessageSource messageSource;
    @Autowired LocaleResolver localeResolver;
    private Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @PostMapping("update/nickname")
    @ResponseBody
    public String updateUserNickName(@Valid @RequestBody UserNewNicknameVO user, Locale locale) {
        userInfoService.updateUserNickNameByID(user.getId(), user.getNickname());

        return messageSource.getMessage("userService.nicknameChanged", null,locale);
    }

    @PostMapping("update/password")
    @ResponseBody
    public String updateUserPassWord(@Valid @RequestBody UserNewPasswordVO user, Locale locale) {
        userInfoService.updateUserPasswordByID(user.getId(), user.getCurrentPass(), user.getNewPass());

        return messageSource.getMessage("userService.passwordChanged", null,locale);
    }

    @ExceptionHandler(PasswordValueNotChangedException.class)
    @ResponseBody
    public ResponseEntity<String> passwordValueNotChangedError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};

        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.passwordNotChanged",rejectvalue ,locale));
    }

    @ExceptionHandler(PasswordValidationFailureException.class)
    @ResponseBody
    public ResponseEntity<String> passwordValidationFailureError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};

        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.passwordValidationFailure",rejectvalue ,locale));
    }
}
