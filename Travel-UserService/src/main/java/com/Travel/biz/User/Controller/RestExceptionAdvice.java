package com.Travel.biz.User.Controller;

import com.Travel.biz.User.Dao.NoSuchUserException;
import com.Travel.biz.User.Service.Info.PasswordValidationFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class RestExceptionAdvice {
    @Autowired MessageSource messageSource;
    private Logger logger = LoggerFactory.getLogger(RestControllerAdvice.class);

    /**
     ** @Valid로 걸러진 Parameter Exception Handler
     **/
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<String> paramViolationError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};
        return ResponseEntity.badRequest().body(messageSource.getMessage(ex.getBindingResult().getFieldError().getDefaultMessage(),rejectvalue ,locale));
    }

    /**
     ** 사용자 정의 Exception
     **/
    @ExceptionHandler(NoSuchUserException.class)
    @ResponseBody
    public ResponseEntity<String> noSuchUserError(NoSuchUserException ex, Locale locale) {
        logger.error(ex.getMessage());
        Object rejectvalue[] = null;
        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.noSuchUser", rejectvalue, locale));
    }

    @ExceptionHandler(PasswordValidationFailureException.class)
    @ResponseBody
    public ResponseEntity<String> passwordValidationFailureError(MethodArgumentNotValidException ex, Locale locale) {
        logger.error(ex.getBindingResult().getFieldError().toString());
        Object rejectvalue[] = {ex.getBindingResult().getFieldError().getRejectedValue()};

        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.passwordValidationFailure",rejectvalue ,locale));
    }
}
