package first.second.third.fuckmylife.service;

import first.second.third.fuckmylife.Entity.OrderList;

import java.util.List;

public interface OrderService {
    void saveOrderList(OrderList orderList);
    List<OrderList> getAllOrders();
    List<OrderList> getAllOrdersByStatus(boolean done);
    void deleteOrder(OrderList orderList);
    void confirmOrder(Long id);

}
