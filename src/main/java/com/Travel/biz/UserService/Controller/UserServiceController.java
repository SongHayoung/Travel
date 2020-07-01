package com.Travel.biz.UserService.Controller;

import com.Travel.Core.Annotations.TODO;
import com.Travel.Core.Jwt.JwtTokenProvider;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.UserService.Dto.UserServiceDto;
import com.Travel.biz.UserService.Service.Info.IncorrectException;
import com.Travel.biz.UserService.Service.Info.NotChangedException;
import com.Travel.biz.UserService.Service.Info.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@TODO("유저 삭제 실패시 에러처리 필요")
@Controller
public class UserServiceController {
    @Autowired UserInfoService userInfoService;
    @Autowired MessageSource messageSource;
    @Autowired JwtTokenProvider jwtTokenProvider;
    private Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @PostMapping("/login")
    @ResponseBody
    public String postLogin(@Valid @RequestBody UserServiceDto.Login user) {
        UserVO targetUser;
        try {
            targetUser = userInfoService.loginUser(user);
        }
        catch (PasswordValidationFailureException e) {
            throw new PasswordValidationFailureException();
        }
        logger.debug(targetUser.getUsername());
        return jwtTokenProvider.createToken(targetUser.getUsername(), targetUser.getRoles());
    }

    @PatchMapping("user/nickname")
    public String updateUserNickname(@Valid @RequestBody UserServiceDto.ChangeNickname user, Locale locale, RedirectAttributes attr) {
        userInfoService.updateUserNickNameByID(user);

        attr.addFlashAttribute(messageSource.getMessage("userService.nicknameChanged", null,locale));

        return "redirect:/user/info";
    }

    @PatchMapping("user/password")
    public String updateUserPassword(@Valid @RequestBody UserServiceDto.ChangePass user, Locale locale, RedirectAttributes attr) {
        try {
            userInfoService.updateUserPasswordByID(user);
        }
        catch (IncorrectException e) {
            throw new PasswordValidationFailureException();
        }
        catch (NotChangedException e) {
            throw new PasswordValueNotChangedException();
        }

        attr.addFlashAttribute("message",messageSource.getMessage("userService.passwordChanged", null,locale));

        return "redirect:/user/info";
    }

    @DeleteMapping("user")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UserServiceDto.Id user, Locale locale) {
        userInfoService.deleteUserByID(user);

        return ResponseEntity.ok(messageSource.getMessage("userService.deleteUser", null, locale));
    }

    @ExceptionHandler(PasswordValueNotChangedException.class)
    @ResponseBody
    public ResponseEntity<String> passwordValueNotChangedError(PasswordValueNotChangedException ex, Locale locale) {
        return ResponseEntity.badRequest().body(messageSource.getMessage("userService.passwordNotChanged",null ,locale));
    }

    @ExceptionHandler(PasswordValidationFailureException.class)
    @ResponseBody
    public ResponseEntity<String> passwordValidationFailureError(PasswordValidationFailureException ex, Locale locale) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageSource.getMessage("userService.passwordValidationFailure",null ,locale));
    }
}
