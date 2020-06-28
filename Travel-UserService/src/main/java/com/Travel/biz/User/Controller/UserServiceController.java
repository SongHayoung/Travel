package com.Travel.biz.User.Controller;

import com.Travel.biz.User.Service.Info.UserInfoService;
import com.Travel.biz.User.VO.UserNewNicknameVO;
import com.Travel.biz.User.VO.UserNewPasswordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@RestController
public class UserServiceController {
    @Autowired UserInfoService userInfoService;
    @Autowired MessageSource messageSource;

    @GetMapping("user")
    public String userInfo(@Valid @RequestBody String id) {
        //userInfoService.
        return null;
    }

    @PostMapping("user/update/changenickname")
    public String updateUserNickName(@Valid @RequestBody UserNewNicknameVO user, Locale locale, RedirectAttributes attr) {
        userInfoService.updateUserNickNameByID(user.getId(), user.getNickname());

        attr.addFlashAttribute(messageSource.getMessage("userService.nicknameChanged", null,locale));

        return "redirect:/user/info";
    }

    @PostMapping("user/update/changepassword")
    public String updateUserPassWord(@Valid @RequestBody UserNewPasswordVO user, Locale locale, RedirectAttributes attr) {
        userInfoService.updateUserPasswordByID(user.getId(), user.getCurrentPass(), user.getNewPass());

        attr.addFlashAttribute("message",messageSource.getMessage("userService.passwordChanged", null,locale));

        return "redirect:/user/info";
    }
}
