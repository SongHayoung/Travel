package com.Travel.Core.User.VO;

import com.Travel.Validation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Data == @Getter @Setter @ToString @EqualAndHashCode
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements UserDetails {
    int userSid;

    @NotNull
    @InCorrectID
    String id;

    @NotNull
    @Email
    String email;

    @NotNull
    @InCorrectPassword
    String password;

    @NotNull
    @InCorrectName
    String name;

    @NotNull
    @Gender
    String gender;

    @NotNull
    @InCorrectNickName
    String nickname;

    List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
