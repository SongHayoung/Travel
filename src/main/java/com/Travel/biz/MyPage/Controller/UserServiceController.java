package com.Travel.biz.MyPage.Controller;

import com.Travel.Core.Annotations.TODO;
import com.Travel.Core.Jwt.JwtTokenProvider;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.Validation.InCorrectNickName;
import com.Travel.biz.MyPage.Dto.UserServiceDto;
import com.Travel.biz.MyPage.Service.Info.IncorrectException;
import com.Travel.biz.MyPage.Service.Info.NotChangedException;
import com.Travel.biz.MyPage.Service.Info.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@TODO("유저 삭제 실패시 에러처리 필요")
@Controller
@RequestMapping("/users")
public class UserServiceController {
    @Autowired UserInfoService userInfoService;
    @Autowired MessageSource messageSource;
    @Autowired JwtTokenProvider jwtTokenProvider;
    private Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @PostMapping("/login")
    public ResponseEntity<String> postLogin(@Valid @RequestBody UserServiceDto.Login user) {
        UserVO targetUser;
        try {
            targetUser = userInfoService.loginUser(user);
        }
        catch (PasswordValidationFailureException e) {
            throw new PasswordValidationFailureException();
        }
        logger.debug(targetUser.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/main");
        headers.add("X-AUTH-TOKEN",jwtTokenProvider.createToken(targetUser.getUsername(), targetUser.getRoles()));
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

    @PatchMapping("/{userId}/nickname/{nickname}")
    public ResponseEntity<String> updateUserNickname(@PathVariable("userId") String userId, @PathVariable("nickname") @Valid @InCorrectNickName String nickname, Locale locale, RedirectAttributes attr) {
        System.out.println(userId + nickname);
        userInfoService.updateUserNickNameByID(userId, nickname);

        attr.addFlashAttribute(messageSource.getMessage("userService.nicknameChanged", null,locale));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/users/" + userId);
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<String> updateUserPassword(@PathVariable("userId") String userId, @Valid @RequestBody UserServiceDto.ChangePass user, Locale locale, RedirectAttributes attr) {
        try {
            userInfoService.updateUserPasswordByID(userId, user);
        }
        catch (IncorrectException e) {
            throw new PasswordValidationFailureException();
        }
        catch (NotChangedException e) {
            throw new PasswordValueNotChangedException();
        }

        attr.addFlashAttribute("message",messageSource.getMessage("userService.passwordChanged", null,locale));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/users/" + userId);
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable("userId") @Valid @RequestBody String userId, Locale locale) {
        userInfoService.deleteUserByID(userId);

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
