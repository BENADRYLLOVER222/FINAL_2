package first.second.third.fuckmylife.service;

import first.second.third.fuckmylife.Entity.OrderList;
import first.second.third.fuckmylife.Entity.Product;
import org.hibernate.service.spi.ServiceException;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(long id);
    void editProduct(Product product);
    List<Product> getActualProducts();
    void substractProduct(OrderList orderList);
    void actualityCheck(Product product) throws ServiceException;
    void checkAndProcessOrder(OrderList orderList) throws Exception;
    void deleteProduct(Long id);

}
