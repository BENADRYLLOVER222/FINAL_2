package first.second.third.fuckmylife.dao.impl;

import first.second.third.fuckmylife.Entity.LoginData;
import first.second.third.fuckmylife.Entity.Product;
import first.second.third.fuckmylife.Entity.User;
import first.second.third.fuckmylife.dao.OrderDao;
import first.second.third.fuckmylife.dao.OrderListDao;
import first.second.third.fuckmylife.dao.UserDao;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public void save(User user) {
        String hql = "FROM User WHERE username = :login";
        User userBuf;
        try {
            userBuf = sessionFactory.getCurrentSession()
                    .createQuery(hql, User.class)
                    .setParameter("login", user.getUsername())
                    .getSingleResult();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().persist(user);
            return;
        }
        if (userBuf != null) {
            throw new RuntimeException("User with this username already exists") {
            };
        }
    }

    @Transactional
    @Override
    public User auth(LoginData loginData) {
        String hql = "FROM User WHERE username = :login";
        User user;
        try {
            user = sessionFactory.getCurrentSession()
                    .createQuery(hql, User.class)
                    .setParameter("login", loginData.getUsername())
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("User not found") {
            };
        }
        if (user != null && user.getPassword().equals(loginData.getPassword())) {
        } else {
            throw new RuntimeException("Wrong login or password") {
            };
        }
        return user;
    }

    @Transactional
    @Override
    public User getByUsername(String username) {
        String hql = "FROM User WHERE username = :login";
        User user;
        try {
            user = sessionFactory.getCurrentSession()
                    .createQuery(hql, User.class)
                    .setParameter("login", username)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("User not found") {
            };
        }
        return user;
    }

    @Transactional
    public List<User> getAllPrivUsers() {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT u FROM User u WHERE u.role = :role";
            return session.createQuery(hql, User.class).setParameter("role", "ADMIN").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public List<User> getAllBlacklisted() {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT u FROM User u WHERE u.role = :role";
            return session.createQuery(hql, User.class).setParameter("role", "MUTED").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void addToBlackList(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, id);
            user.setRole("MUTED");
            session.merge(user);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user role to MUTE: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void changeUserRole(String username, String role) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "SELECT u FROM User u WHERE u.username = :name";
            User user = session.createQuery(hql, User.class).setParameter("name", username).getSingleResult();
            user.setRole(role   );
            session.merge(user);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user role to ADMIN: " + e.getMessage(), e);
        }
    }
}
