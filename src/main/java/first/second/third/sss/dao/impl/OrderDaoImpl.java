package first.second.third.fuckmylife.dao.impl;

import first.second.third.fuckmylife.Entity.Order;
import first.second.third.fuckmylife.dao.OrderDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Order order) {
        try {
            getCurrentSession().merge(order);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findById(Long id) {
        return getCurrentSession().get(Order.class, id);
    }

    @Override
    public List<Order> findAll() {
        Query<Order> query = getCurrentSession().createQuery("from Order", Order.class);
        return query.getResultList();
    }

    @Override
    public void delete(Order order) {
        getCurrentSession().remove(order);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}