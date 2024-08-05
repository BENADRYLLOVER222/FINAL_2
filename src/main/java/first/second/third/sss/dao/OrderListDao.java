package first.second.third.fuckmylife.dao;

import first.second.third.fuckmylife.Entity.OrderList;

import java.util.List;

public interface OrderListDao {
    void save(OrderList orderList);
    OrderList findById(Long id);
    List<OrderList> findAll();
    void delete(OrderList orderList);
    List<OrderList> findAllByStatus(boolean done);
    public void deleteByUserId(long id);
    public void confirmOrder(OrderList orderList);

}