package lanami.example.spingsecurity.service;

import lanami.example.spingsecurity.domain.User;

import java.util.List;

/**
 * @author lanami
 * @date 2016-09-06
 */
public interface IUserService {
    User findById(long id);

    User findByUsername(String username);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserByUsername(String username);

    List<User> findAllUsers();

    boolean isUsernameUnique(Long id, String username);
}
