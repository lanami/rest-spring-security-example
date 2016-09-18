package lanami.example.spingsecurity.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Lana Kolupaeva
 * @date 2016-08-30
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{3,}$")
    @Size(min=3, max = 60) //bcrypt hash length = 60
    private String password;

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(min = 2, max = 50)
    private String firstName;

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(min = 2, max = 50)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<UserRole> roles = new HashSet<UserRole>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
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