package first.second.third.fuckmylife.dao.impl;

import first.second.third.fuckmylife.Entity.*;
import first.second.third.fuckmylife.dao.ProductDao;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addProduct(Product product) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(product);
    }

    @Override
    public void deleteProduct(Long id){
        Session session = sessionFactory.getCurrentSession();
        try {
            Product product = session.get(Product.class, id);
            session.remove(product);
        } catch(Exception e){
            throw new RuntimeException("Product does not exists");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p FROM Product p " +
                "LEFT JOIN FETCH p.size s " +
                "LEFT JOIN FETCH p.image i";
        List<Product> products = session.createQuery(hql, Product.class).getResultList();
        return products;
    }

    @Override
    public List<Product> getActualProducts() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p FROM Product p " +
                "LEFT JOIN FETCH p.size s " +
                "LEFT JOIN FETCH p.image i " +
                "WHERE s.xs != 0 OR s.s != 0 OR s.m != 0 OR s.l != 0 OR s.xl != 0 OR s.xxl != 0 OR s.xxxl != 0";
        List<Product> products = session.createQuery(hql, Product.class).getResultList();
        return products;
    }

    @Override
    public void actualityCheck(Product product) throws Exception {
        if(product.getSize().getXs() == 0 && product.getSize().getS() == 0 && product.getSize().getM() == 0 &&
                product.getSize().getL() == 0 && product.getSize().getXl() == 0 && product.getSize().getXxl() == 0 &&
                product.getSize().getXxxl() == 0) {
            throw new Exception("Sorry, this product is out of stock!");
        }
    }


    @Override
    public void substractProduct(OrderList orderList) {
        Session session = sessionFactory.getCurrentSession();
        for (Order order : orderList.getOrders()) {
            Product product = session.get(Product.class, order.getProduct().getId());
            ProductSize size = product.getSize();

            // Изменяем размер в зависимости от размера, указанных в заказе
            switch (order.getSize()) {
                case "X-Small":
                    size.setXs(size.getXs() - order.getAmount());
                    break;
                case "Small":
                    size.setS(size.getS() - order.getAmount());
                    break;
                case "Medium":
                    size.setM(size.getM() - order.getAmount());
                    break;
                case "Large":
                    size.setL(size.getL() - order.getAmount());
                    break;
                case "X-Large":
                    size.setXl(size.getXl() - order.getAmount());
                    break;
                case "XX-Large":
                    size.setXxl(size.getXxl() - order.getAmount());
                    break;
                case "XXX-Large":
                    size.setXxxl(size.getXxxl() - order.getAmount());
                    break;
            }
            // Обновляем объект Product
            session.merge(product);
        }
    }

    @Override
    public void checkAndProcessOrder(OrderList orderList) throws Exception {
        Session session = sessionFactory.getCurrentSession();

        // Проверка наличия достаточного количества товара
        for (Order order : orderList.getOrders()) {
            Product product = session.get(Product.class, order.getProduct().getId());

            if (product == null) {
                throw new Exception("Product with ID " + order.getProduct().getId() + " not found.");
            }

            ProductSize size = product.getSize();

            // Проверка, достаточно ли товара в наличии для каждого размера
            switch (order.getSize()) {
                case "X-Small":
                    if (size.getXs() < order.getAmount()) {
                        throw new Exception("Not enough stock for size X-Small of product " + product.getName());
                    }
                    break;
                case "Small":
                    if (size.getS() < order.getAmount()) {
                        throw new Exception("Not enough stock for size Small of product " + product.getName());
                    }
                    break;
                case "Medium":
                    if (size.getM() < order.getAmount()) {
                        throw new Exception("Not enough stock for size Medium of product " + product.getName());
                    }
                    break;
                case "Large":
                    if (size.getL() < order.getAmount()) {
                        throw new Exception("Not enough stock for size Large of product " + product.getName());
                    }
                    break;
                case "X-Large":
                    if (size.getXl() < order.getAmount()) {
                        throw new Exception("Not enough stock for size X-Large of product " + product.getName());
                    }
                    break;
                case "XX-Large":
                    if (size.getXxl() < order.getAmount()) {
                        throw new Exception("Not enough stock for size XX-Large of product " + product.getName());
                    }
                    break;
                case "XXX-Large":
                    if (size.getXxxl() < order.getAmount()) {
                        throw new Exception("Not enough stock for size XXX-Large of product " + product.getName());
                    }
                    break;
            }
        }
    }

    @Override
    public Product getProductById(long id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p FROM Product p " +
                "LEFT JOIN FETCH p.size s " +
                "LEFT JOIN FETCH p.image i " +
                "WHERE p.id = :productId";
        Product product = session.createQuery(hql, Product.class)
                .setParameter("productId", id)
                .uniqueResult();
        return product;
    }

    @Override
    public void editProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();

        // Загрузка существующих сущностей из базы данных
        ProductSize existingSize = session.get(ProductSize.class, product.getSize().getId());
        if (existingSize != null) {
            existingSize.setXs(product.getSize().getXs());
            existingSize.setS(product.getSize().getS());
            existingSize.setM(product.getSize().getM());
            existingSize.setL(product.getSize().getL());
            existingSize.setXl(product.getSize().getXl());
            existingSize.setXxl(product.getSize().getXxl());
            existingSize.setXxxl(product.getSize().getXxxl());
        }

        ProductImage existingImage = session.get(ProductImage.class, product.getImage().getId());
        if (existingImage != null) {
            existingImage.setImage1(product.getImage().getImage1());
            existingImage.setImage2(product.getImage().getImage2());
            existingImage.setImage3(product.getImage().getImage3());
            existingImage.setImage4(product.getImage().getImage4());
        }

        // Сохранение или обновление сущностей
        session.saveOrUpdate(existingSize);
        session.saveOrUpdate(existingImage);

        // Обновление продукта с новыми связанными сущностями
        product.setSize(existingSize);
        product.setImage(existingImage);
        session.saveOrUpdate(product);
    }

    public void sendOrder(OrderList orderList) {
    }
}
