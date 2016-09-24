package lanami.example.springsecurity.persistence;

import lanami.example.springsecurity.domain.UserRole;

import java.util.List;

/**
 * @author lanami
 * @date 2016-09-06
 */
public interface IUserRoleDAO {
    List<UserRole> findAll();
    UserRole findByType(String type);
    UserRole findById(long id);
}
