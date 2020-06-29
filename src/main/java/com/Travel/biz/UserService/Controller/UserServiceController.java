package com.Travel.biz.UserService.Controller;

import com.Travel.biz.UserService.Dto.UserIdDto;
import com.Travel.biz.UserService.Service.Info.UserInfoService;
import com.Travel.biz.UserService.Dto.UserLoginDto;
import com.Travel.biz.UserService.Dto.UserNicknameDto;
import com.Travel.biz.UserService.Dto.UserPasswordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@RestController
public class UserServiceController {
    @Autowired UserInfoService userInfoService;
    @Autowired MessageSource messageSource;

    @PostMapping("/login")
    public String postLogin(@Valid @RequestBody UserLoginDto user) {
        userInfoService.loginUser(user);

        return "redirect:/main";
    }

    @PatchMapping("user/nickname")
    public String updateUserNickname(@Valid @RequestBody UserNicknameDto user, Locale locale, RedirectAttributes attr) {
        userInfoService.updateUserNickNameByID(user);

        attr.addFlashAttribute(messageSource.getMessage("userService.nicknameChanged", null,locale));

        return "redirect:/user/info";
    }

    @PatchMapping("user/password")
    public String updateUserPassword(@Valid @RequestBody UserPasswordDto user, Locale locale, RedirectAttributes attr) {
        userInfoService.updateUserPasswordByID(user);

        attr.addFlashAttribute("message",messageSource.getMessage("userService.passwordChanged", null,locale));

        return "redirect:/user/info";
    }

    @DeleteMapping("user")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UserIdDto user, Locale locale) {
        userInfoService.deleteUserByID(user);

        return ResponseEntity.ok(messageSource.getMessage("userService.deleteUser", null, locale));
    }
}
