package newlab3;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DBService {

    private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "update";

    private final SessionFactory sessionFactory;


    public DBService(String login, String pwd) throws HibernateException {
        Configuration configuration = setH2Configuration(login, pwd);

        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration setH2Configuration(String login, String pwd) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./newlab3_db");
        configuration.setProperty("hibernate.connection.username", login);
        configuration.setProperty("hibernate.connection.password", pwd);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public long addUser(String login, String hashPassword) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserDAO dao = new UserDAO(session);
            long userID = dao.insertUser(login,hashPassword);
            transaction.commit();
            session.close();
            return userID;
        } catch (HibernateException e) {
            System.out.println(e);
        }
        return -1;
    }

    public User getUser(String login) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserDAO dao = new UserDAO(session);
            User user = dao.getUser(login);
            transaction.commit();
            session.close();
            return user;
        } catch (HibernateException e) {
            System.out.println(e);
        }
        return null;
    }


    public void closeConnection() {
        sessionFactory.close();
    }
}
