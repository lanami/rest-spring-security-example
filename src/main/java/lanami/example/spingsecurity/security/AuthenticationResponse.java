package lanami.example.spingsecurity.security;

import lanami.example.spingsecurity.domain.User;
import lanami.example.spingsecurity.domain.UserRole;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-08
 */
public class AuthenticationResponse implements Serializable {
    private String token;
    private String username;
    Set<UserRole> roles;

    public AuthenticationResponse() {
        super();
    }

    public AuthenticationResponse(User user, String token) {
        this.token = token;
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
