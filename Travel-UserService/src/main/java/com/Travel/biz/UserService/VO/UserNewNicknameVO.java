package com.Travel.biz.UserService.VO;

import com.Travel.Validation.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserNewNicknameVO {
    @InCorrectID
    String id;

    @InCorrectNickName
    String nickname;
}
