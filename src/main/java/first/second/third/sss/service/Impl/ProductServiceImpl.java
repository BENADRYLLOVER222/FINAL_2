package first.second.third.fuckmylife.service.Impl;

import first.second.third.fuckmylife.Entity.OrderList;
import first.second.third.fuckmylife.Entity.Product;
import first.second.third.fuckmylife.dao.ProductDao;
import first.second.third.fuckmylife.service.ProductService;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.PropertyResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    @Override
    @Transactional
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    @Transactional
    public Product getProductById(long id) {
        return productDao.getProductById(id);
    }

    @Override
    @Transactional
    public void editProduct(Product product) {productDao.editProduct(product);}

    @Override
    @Transactional
    public List<Product> getActualProducts(){return productDao.getActualProducts();}

    @Override
    @Transactional
    public void substractProduct(OrderList orderList){productDao.substractProduct(orderList);}

    @Override
    @Transactional
    public void actualityCheck(Product product) throws ServiceException{
        try {
            productDao.actualityCheck(product);
        } catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void checkAndProcessOrder(OrderList orderList) throws ServiceException {
        try{
            productDao.checkAndProcessOrder(orderList);
        } catch (Exception e){
            throw new ServiceException(e.getMessage());
        };
    }
    public void deleteProduct(Long id){
        try{
            productDao.deleteProduct(id);
        } catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }
}
