package pl.confitura.jelatyna.user.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ADMIN, VOLUNTEER;

    @Override
    public String getAuthority() {
        return name();
    }
}
