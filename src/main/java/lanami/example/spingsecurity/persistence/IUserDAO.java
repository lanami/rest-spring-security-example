package lanami.example.spingsecurity.persistence;

import lanami.example.spingsecurity.domain.User;

import java.util.List;

/**
 * @author Lana Kolupaeva
 * @date 2016-08-30
 */
public interface IUserDAO {
    User findById(long id);
    User findByUsername(String username);
    void save(User user);
    void deleteByUsername(String username);
//    void register(User user) throws UserAlreadyExistsException;
//    boolean isUsernameAvailable(String username);
    List<User> findAllUsers();

}
