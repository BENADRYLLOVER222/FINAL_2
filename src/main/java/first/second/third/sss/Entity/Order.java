package first.second.third.fuckmylife.Entity;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id") // Убедитесь, что имя колонки соответствует таблице
    private long id; // order id

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "order_list_id")
    private OrderList orderList;

    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }


    // геттеры и сеттеры
    public void setProduct(Product product){
        this.product = product;
    }

    public Product getProduct(){
        return product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}