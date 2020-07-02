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
    public static class Login {
        String id;

        String password;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePass {
        @NotNull
        @NotBlank
        @InCorrectPassword
        String currentPassword;

        @NotNull
        @NotBlank
        @InCorrectPassword
        String newPassword;
    }
}
