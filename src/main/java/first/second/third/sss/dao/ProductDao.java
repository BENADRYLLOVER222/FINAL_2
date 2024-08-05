package first.second.third.fuckmylife.dao;

import first.second.third.fuckmylife.Entity.OrderList;
import first.second.third.fuckmylife.Entity.Product;

import java.util.List;

public interface ProductDao {
void addProduct(Product product);
//void removeProduct(String product);
List<Product> getAllProducts();
Product getProductById(long id);
void editProduct(Product product);
List<Product> getActualProducts();
void substractProduct(OrderList orderList);
void actualityCheck(Product product) throws Exception;
void checkAndProcessOrder(OrderList orderList) throws Exception;
void deleteProduct(Long id);
/*void addImages(String product, List<String> images);
void addSizes(String product, HashMap<String, Integer> sizes);
void updateImages(String product, List<String> images);
void updateSizes(String product, HashMap<String, Integer> sizes); */
}
