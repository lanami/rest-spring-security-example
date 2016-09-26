package lanami.example.springsecurity.app.service;

import lanami.example.springsecurity.app.domain.UserRole;

import java.util.List;

/**
 * @author lanami
 * @date 2016-09-06
 */
public interface IUserRoleService {
    UserRole findById(long id);

    UserRole findByType(String type);

    List<UserRole> findAll();
}
