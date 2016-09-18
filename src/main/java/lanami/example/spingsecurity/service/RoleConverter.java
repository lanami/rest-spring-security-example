package lanami.example.spingsecurity.service;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-06
 */

import lanami.example.spingsecurity.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<Object, UserRole> {

    @Autowired
    IUserRoleService userRoleService;

    public UserRole convert(Object element) {
        Integer id = Integer.parseInt((String) element);
        UserRole role = userRoleService.findById(id);
        return role;
    }
}

