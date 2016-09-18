package lanami.example.spingsecurity.persistence;

import lanami.example.spingsecurity.domain.UserRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lana Kolupaeva
 * @date 2016-09-06
 */
@Repository("userProfileDao")
public class UserRoleDAO extends AbstractDAO<Long, UserRole> implements IUserRoleDAO {

    public UserRole findById(long id) {
        return getByKey(id);
    }

    public UserRole findByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (UserRole) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<UserRole> findAll(){
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<UserRole>)crit.list();
    }

}