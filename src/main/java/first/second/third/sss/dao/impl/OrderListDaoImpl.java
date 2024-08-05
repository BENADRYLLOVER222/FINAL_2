package first.second.third.fuckmylife.dao.impl;

import first.second.third.fuckmylife.Entity.OrderList;
import first.second.third.fuckmylife.dao.OrderListDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderListDaoImpl implements OrderListDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void save(OrderList orderList) {
        try {
            getCurrentSession().saveOrUpdate(orderList);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderList findById(Long id) {
        return getCurrentSession().get(OrderList.class, id);
    }

    @Override
    public List<OrderList> findAll() {
        Query<OrderList> query = getCurrentSession().createQuery("from OrderList", OrderList.class);
        return query.getResultList();
    }

    @Override
    public List<OrderList> findAllByStatus(boolean done) {
        Query<OrderList> query = getCurrentSession().createQuery("from OrderList where done = :doneStatus", OrderList.class);
        query.setParameter("doneStatus", done);
        return query.getResultList();
    }

    @Override
    public void deleteByUserId(long id){
        Query query = getCurrentSession().createQuery("delete from OrderList where user_id = :userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }

    @Override
    public void confirmOrder(OrderList orderList){
        orderList.setDone(true);
        sessionFactory.getCurrentSession().merge(orderList);
    }
    @Override
    public void delete(OrderList orderList) {
        sessionFactory.getCurrentSession().remove(orderList);
    }
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}