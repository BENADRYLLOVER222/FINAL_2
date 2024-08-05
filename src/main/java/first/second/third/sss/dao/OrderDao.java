package first.second.third.fuckmylife.dao;

import first.second.third.fuckmylife.Entity.Order;

import java.util.List;

public interface OrderDao {
    void save(Order order);
    Order findById(Long id);
    List<Order> findAll();
    void delete(Order order);
}