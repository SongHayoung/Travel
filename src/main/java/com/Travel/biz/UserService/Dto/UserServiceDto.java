package com.Travel.biz.UserService.Dto;

import com.Travel.Validation.InCorrectID;
import com.Travel.Validation.InCorrectNickName;
import com.Travel.Validation.InCorrectPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserServiceDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id {
        @NotNull
        @NotBlank
        @InCorrectID
        String id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {
        @NotNull
        @NotBlank
        @InCorrectID
        String id;

        @NotNull
        @NotBlank
        @InCorrectPassword
        String pass;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePass {

        @NotNull
        @NotBlank
        @InCorrectID
        String id;

        @NotNull
        @NotBlank
        @InCorrectPassword
        String currentPass;

        @NotNull
        @NotBlank
        @InCorrectPassword
        String newPass;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangeNickname {
        @NotNull
        @NotBlank
        @InCorrectID
        String id;

        @NotNull
        @NotBlank
        @InCorrectNickName
        String nickname;
    }

}
