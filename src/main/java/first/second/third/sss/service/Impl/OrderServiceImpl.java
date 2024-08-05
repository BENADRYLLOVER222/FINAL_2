package first.second.third.fuckmylife.service.Impl;

import first.second.third.fuckmylife.Entity.Order;
import first.second.third.fuckmylife.Entity.OrderList;
import first.second.third.fuckmylife.dao.OrderDao;
import first.second.third.fuckmylife.dao.OrderListDao;
import first.second.third.fuckmylife.service.OrderService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderListDao orderListDao;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void saveOrderList(OrderList orderList) {
        try {
            orderListDao.save(orderList);
            for (Order order : orderList.getOrders()) {
                order.setOrderList(orderList);
                orderDao.save(order);
            }
        } catch (Exception e) {
            throw e; // Перебрасываем исключение, чтобы транзакция была отменена
        }
    }

    @Transactional
    public void deleteOrder(OrderList orderList) {
        if(!orderList.isDone()) {
            try {
                for (Order order : orderList.getOrders()) {
                    orderDao.delete(order);
                }
                orderListDao.delete(orderList);
            } catch (Exception e) {
                throw new ServiceException("Order not found");
            }
        }
    }

    @Transactional
    public List<OrderList> getAllOrders() {
        return orderListDao.findAll();
    }

    @Transactional
    public List<OrderList> getAllOrdersByStatus(boolean done) {
        return orderListDao.findAllByStatus(done);
    }

    @Transactional
    public void confirmOrder(Long id){
        orderListDao.confirmOrder(orderListDao.findById(id));
    }
}