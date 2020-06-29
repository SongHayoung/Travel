package com.Travel.Core.ControllerAdvice;

import com.Travel.Core.User.Dao.DuplicateKeyException;
import com.Travel.biz.UserService.Controller.PasswordValueNotChangedException;
import com.Travel.biz.UserService.Service.Info.NoSuchUserException;
import com.Travel.biz.UserService.Controller.PasswordValidationFailureException;
import com.Travel.biz.UserService.Controller.DuplicateUserEmailException;
import com.Travel.biz.UserService.Controller.DuplicateUserIDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @Autowired MessageSource messageSource;
    private Logger logger = LoggerFactory.getLogger(RestControllerAdvice.class);

    /**
     ** @Valid로 걸러진 Parameter Exception Handler
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> paramViolationError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};
        return ResponseEntity.badRequest().body(messageSource.getMessage(ex.getBindingResult().getFieldError().getDefaultMessage(),rejectvalue ,locale));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> noSuchUserError(Exception ex, Locale locale) {

        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.unDefinedError", null, locale));
    }

    /**
     ** 사용자 정의 Exception
     **/
    @ExceptionHandler(NoSuchUserException.class)
    @ResponseBody
    public ResponseEntity<String> noSuchUserError(NoSuchUserException ex, Locale locale) {
        Object rejectvalue[] = {ex.getMessage()};

        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.noSuchUser", rejectvalue, locale));
    }
}
