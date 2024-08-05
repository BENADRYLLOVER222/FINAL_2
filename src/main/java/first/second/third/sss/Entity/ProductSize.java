package first.second.third.fuckmylife.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "product_sizes")
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "xs")
    private int xs;

    @Column(name = "s")
    private int s;

    @Column(name = "m")
    private int m;

    @Column(name = "l")
    private int l;

    @Column(name = "xl")
    private int xl;

    @Column(name = "xxl")
    private int xxl;

    @Column(name = "xxxl")
    private int xxxl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getXs() {
        return xs;
    }

    public void setXs(int xs) {
        this.xs = xs;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getXl() {
        return xl;
    }

    public void setXl(int xl) {
        this.xl = xl;
    }

    public int getXxl() {
        return xxl;
    }

    public void setXxl(int xxl) {
        this.xxl = xxl;
    }

    public int getXxxl() {
        return xxxl;
    }

    public void setXxxl(int xxxl) {
        this.xxxl = xxxl;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Геттеры и сеттеры для всех полей, включая id
    // Пустой конструктор и другие методы, если необходимо
}