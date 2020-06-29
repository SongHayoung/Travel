package com.Travel.biz.UserService.Dto;

import com.Travel.Validation.InCorrectID;
import com.Travel.Validation.InCorrectPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDto {

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
