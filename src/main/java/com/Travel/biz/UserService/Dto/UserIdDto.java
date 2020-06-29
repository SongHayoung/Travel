package com.Travel.biz.UserService.Dto;

import com.Travel.Validation.InCorrectID;
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
public class UserIdDto {
    @NotNull
    @NotBlank
    @InCorrectID
    String id;
}
