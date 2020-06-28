package com.Travel.biz.User.VO;

import com.Travel.Validation.InCorrectID;
import com.Travel.Validation.InCorrectPassword;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserNewPasswordVO {
    @InCorrectID
    String id;

    @InCorrectPassword
    String currentPass;

    @InCorrectPassword
    String newPass;
}
