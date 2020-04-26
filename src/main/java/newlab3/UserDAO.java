package newlab3;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserDAO {
    private Session session;

    UserDAO(Session session) {
        this.session=session;
    }

    public long insertUser(String login, String hashPassword) throws HibernateException {
        User user=new User(login,hashPassword);
        return (Long) session.save(user);
    }

    public User getUser(String login) {
        Criteria criteria=session.createCriteria(User.class);
        return (User) criteria.add(Restrictions.eq("login",login)).uniqueResult();
    }
}
