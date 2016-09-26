package lanami.example.springsecurity.app.service;

import lanami.example.springsecurity.app.domain.User;
import lanami.example.springsecurity.app.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * Extend Spring Security User to hold additional properties.
 * Think of UserDetails as the adapter between your own user database and what Spring Security needs inside the SecurityContextHolder. B

 * @author lanami
 * @author 2016-09-05
 */
@Service("userDetailsService")
public class TheUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    MessageSource messageSource;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException(messageSource.getMessage("error.usernameNotFound", new String[]{user.getUsername()}, Locale.getDefault()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(UserRole userRole : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getType()));
        }
        return authorities;
    }
}
