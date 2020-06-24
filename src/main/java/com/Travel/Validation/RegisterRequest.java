package com.Travel.Validation;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {
    @Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$")
    String id;

    @Pattern(regexp = "^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$")
    String email;

    @Size(min = 8, max = 15)
    String pass;

    @Size(min = 2, max = 5)
    String name;

    @Pattern(regexp = "^[F,M]{1}$")
    String gender;

    @Size(min = 1, max = 20)
    String nickname;
}
