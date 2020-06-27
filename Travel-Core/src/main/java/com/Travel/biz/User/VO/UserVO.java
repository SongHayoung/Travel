package com.Travel.biz.User.VO;

import com.Travel.Validation.*;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @Data == @Getter @Setter @ToString @EqualAndHashCode
 */
@Data
@Builder
public class UserVO {
    @NotNull
    @InCorrectID
    String id;

    @NotNull
    @Email
    String email;

    @NotNull
    @InCorrectPassword
    String pass;

    @NotNull
    @InCorrectName
    String name;

    @NotNull
    @Gender
    String gender;

    @NotNull
    @InCorrectNickName
    String nickname;
}
