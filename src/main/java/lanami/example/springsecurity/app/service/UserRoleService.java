package lanami.example.springsecurity.app.service;

import lanami.example.springsecurity.app.domain.UserRole;
import lanami.example.springsecurity.app.persistence.IUserRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lanami
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

