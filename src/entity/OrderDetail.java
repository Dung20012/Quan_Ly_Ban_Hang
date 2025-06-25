package entity;

public class OrderDetail {
//    Mỗi đơn hàng có thể có nhiều sản phẩm: OrderDetail sẽ lưu:
//    Đơn hàng nào (order_id)
//    Sản phẩm nào (product_id)
//    Số lượng mua
//    Giá tại thời điểm đặt

    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double unitPrice;

//    COnstructor k tham số
    public OrderDetail() {
    }
//    Constructor có tham số
    public OrderDetail(int id, int orderId, int productId, int quantity, double unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

//    Getter / Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

//    Phương thức hiển thị thông tin chi tiết đơn hàng
    @Override
    public String toString() {
        return String.format("Product ID: %d | Quantity: %d | Unit Price: %.2f | Total: %.2f",
                productId, quantity, unitPrice, quantity * unitPrice);
    }
}
