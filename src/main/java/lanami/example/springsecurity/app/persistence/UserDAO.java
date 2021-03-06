package lanami.example.springsecurity.app.persistence;

import lanami.example.springsecurity.app.domain.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lanami
 * @date 2016-09-06
 */
@Repository("userDao")
public class UserDAO extends AbstractDAO<Long, User> implements IUserDAO {

    public User findById(long id) {
        User user = getByKey(id);
        if(user!=null){
            Hibernate.initialize(user.getRoles());
        }
        return user;
    }

    public User findByUsername(String username) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("username", username));
        User user = (User)crit.uniqueResult();
        if(user!=null){
            Hibernate.initialize(user.getRoles());
        }
        return user;
    }

    public List<User> findAllUsers() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<User> users = (List<User>) criteria.list();
        return users;
    }

    public void save(User user) {
        persist(user);
    }

    public void deleteByUsername(String username) {
        Criteria criteria = createEntityCriteria().add(Restrictions.eq("username", username));
        User user = (User)criteria.uniqueResult();
        delete(user);
    }

}
