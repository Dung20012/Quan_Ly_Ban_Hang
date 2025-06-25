package entity;

public class Product {
    private int id; // Mã sản phẩm
    private String name; // Tên sản phẩm
    private double price; // Giá sản phẩm
    private int stock; // Số lượng tồn kho

//    Constructor không tham số
    public Product() {
    }
//    Constructor đầy đủ tham số
    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

//    Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

//    Hiển thị thông tin sản phẩm
    @Override
    public String toString() {
        return String.format("ID: %d |  Name: %s |  Price: %.2f |  Stock: %d",
                id, name, price, stock);
    }
}
