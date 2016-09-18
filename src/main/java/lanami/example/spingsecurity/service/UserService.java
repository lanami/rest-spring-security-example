package lanami.example.spingsecurity.service;

import lanami.example.spingsecurity.domain.User;
import lanami.example.spingsecurity.domain.UserRole;
import lanami.example.spingsecurity.persistence.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Lana Kolupaeva
 * @date 2016-08-30
 */

@Service("userService")
@Transactional
public class UserService implements IUserService {

    @Autowired
    private IUserDAO dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRoleService userRoleService;

    public User findById(long id) {
        return dao.findById(id);
    }

    public User findByUsername(String username) {
        User user = dao.findByUsername(username);
        return user;
    }

    public void saveUser(User user) {
        if (user.getRoles().isEmpty()) { //if new user has no roles (i.e. is being created by an anonymous) - assign ROLE_USER
            UserRole role = userRoleService.findByType("USER");
            user.getRoles().add(role);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setUsername(user.getUsername());
            if(!user.getPassword().equals(entity.getPassword())){
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setRoles(user.getRoles());
        }
    }


    public void deleteUserByUsername(String username) {
        dao.deleteByUsername(username);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    public boolean isUsernameUnique(Long id, String username) {
        User user = findByUsername(username);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }

}
