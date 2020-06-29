package com.Travel.Core.User.VO;

import com.Travel.Validation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * @Data == @Getter @Setter @ToString @EqualAndHashCode
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
