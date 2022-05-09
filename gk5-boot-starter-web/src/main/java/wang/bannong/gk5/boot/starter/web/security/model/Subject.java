package wang.bannong.gk5.boot.starter.web.security.model;

import java.util.Collection;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wang.bannong.gk5.boot.starter.web.gateway.SubjectClient;

/**
 * 登录模型
 */
@Data
public class Subject implements UserDetails {
    private static final long serialVersionUID = -6399093911875281509L;

    private String username;

    private String password;

    private Long subjectId;

    private SubjectClient client;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
