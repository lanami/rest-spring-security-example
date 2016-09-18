package lanami.example.spingsecurity.persistence;

import lanami.example.spingsecurity.domain.UserRole;

import java.util.List;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-06
 */
public interface IUserRoleDAO {
    List<UserRole> findAll();
    UserRole findByType(String type);
    UserRole findById(long id);
}
