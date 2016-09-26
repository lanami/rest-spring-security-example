package lanami.example.springsecurity.app.security;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lanami
 * @date 2016-09-08
 */
public class AuthenticationRequest implements Serializable {
    @NotNull
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
