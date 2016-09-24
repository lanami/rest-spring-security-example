package lanami.example.springsecurity.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * Extend Spring Security User to hold additional properties.
 *
 * @author Lana Kolupaeva
 * @date 2016-08-30
 */
@Entity
@Table(name = "user")
public class User extends org.springframework.security.core.userdetails.User {
    private Long id;

//    private String username;
//    private String password;

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(min = 2, max = 50)
    private String firstName;

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(min = 2, max = 50)
    private String lastName;

//    private Set<UserRole> roles = new HashSet<UserRole>();

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(min = 3, max = 50)
    @Column(unique = true)
    public String getUsername() {
        return super.getUsername();
    }

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{3,}$")
    @Size(min=3, max = 60) //bcrypt hash length = 60
    public String getPassword() {
        return super.getPassword();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 17)
                .append(id)
                .append(username)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        User other = (User) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(id, other.id)
                .append(username, other.username)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("username", username).
                append("firstName", firstName).
                append("lastName", lastName).
                toString();
    }
}
