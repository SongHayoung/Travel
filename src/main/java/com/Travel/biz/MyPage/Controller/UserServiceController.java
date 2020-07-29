package com.Travel.biz.MyPage.Controller;

import com.Travel.Core.Annotations.TODO;
import com.Travel.Core.Jwt.JwtTokenProvider;
import com.Travel.Core.User.Service.UserCoreService;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.Validation.InCorrectNickName;
import com.Travel.biz.MyPage.Dto.UserServiceDto;
import com.Travel.biz.MyPage.Service.Info.IncorrectException;
import com.Travel.biz.MyPage.Service.Info.NotChangedException;
import com.Travel.biz.MyPage.Service.Info.UserInfoService;
import io.swagger.annotations.ApiOperation;
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
import java.util.List;
import java.util.Locale;

@TODO("유저 삭제 실패시 에러처리 필요")
@Controller
@RequestMapping("/users")
public class UserServiceController {
    @Autowired UserInfoService userInfoService;
    @Autowired UserCoreService userCoreService;
    @Autowired MessageSource messageSource;
    @Autowired JwtTokenProvider jwtTokenProvider;
    private Logger logger = LoggerFactory.getLogger(UserServiceController.class);

    @PostMapping("/login")
    @ApiOperation(value = "/user/login", notes = "사용자 로그인")
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
    @ApiOperation(value = "/user/{userId}/nickname/{nickname}", notes = "사용자 닉네임 변경")
    public ResponseEntity<String> updateUserNickname(@PathVariable("userId") String userId, @PathVariable("nickname") @Valid @InCorrectNickName String nickname, Locale locale, RedirectAttributes attr) {
        System.out.println(userId + nickname);
        userInfoService.updateUserNickNameByID(userId, nickname);

        attr.addFlashAttribute(messageSource.getMessage("userService.nicknameChanged", null,locale));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/users/" + userId);
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

    @PatchMapping("/{userId}/password")
    @ApiOperation(value = "/user/{userId}/password", notes = "사용자 비밀번호 변경")
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
    @ApiOperation(value = "/user/{userId}", notes = "사용자 탈퇴")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable("userId") @Valid @RequestBody String userId, Locale locale) {
        userInfoService.deleteUserByID(userId);

        return ResponseEntity.ok(messageSource.getMessage("userService.deleteUser", null, locale));
    }

    @PostMapping("/{follower}/follow/{following}")
    @ApiOperation(value = "/user/{follower}/follow/{following}", notes = "사용자 팔로우")
    @ResponseBody
    public ResponseEntity<String> followUser(@PathVariable("follower") String follower, @PathVariable("following") String following, Locale locale) {
        userCoreService.follow(follower, following);

        return ResponseEntity.ok(messageSource.getMessage("userService.follow", null, locale));
    }

    @PostMapping("/{follower}/unfollow/{following}")
    @ApiOperation(value = "/user/{follower}/unfollow/{following}", notes = "사용자 언팔로우")
    @ResponseBody
    public ResponseEntity<String> unFollowUser(@PathVariable("follower") String follower, @PathVariable("following") String following, Locale locale) {
        userCoreService.unFollow(follower, following);

        return ResponseEntity.ok(messageSource.getMessage("userService.unFollow", null, locale));
    }

    @GetMapping("/{userId}/followings")
    @ApiOperation(value = "/user/{userId}/followings", notes = "사용자 팔로잉 유저 제공")
    @ResponseBody
    public List<UserVO> getFollowings(@PathVariable("userId") String userId, Locale locale) {
        return userCoreService.getFollowings(userId);
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
