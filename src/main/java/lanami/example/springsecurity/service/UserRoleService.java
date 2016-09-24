package lanami.example.springsecurity.service;

import lanami.example.springsecurity.domain.UserRole;
import lanami.example.springsecurity.persistence.IUserRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-06
 */
@Service("userRoleService")
@Transactional
public class UserRoleService implements IUserRoleService {

    @Autowired
    IUserRoleDAO dao;

    public UserRole findById(long id) {
        return dao.findById(id);
    }

    public UserRole findByType(String type){
        return dao.findByType(type);
    }

    public List<UserRole> findAll() {
        return dao.findAll();
    }
}

