package lanami.example.springsecurity.persistence;

import lanami.example.springsecurity.domain.User;

import java.util.List;

/**
 * @author lanami
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
