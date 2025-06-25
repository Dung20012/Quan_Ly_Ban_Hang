package entity;

import java.sql.Timestamp;

public class Order {
    private int id; // id đơn hàng
    private int customerId; // id khách hàng
    private Timestamp orderDate; // ngày đặt
    private double totalAmount; // tổng tiền

//    Constructor không tham số
    public Order() {
    }
//    Constructor đầy đủ tham số
    public Order(int id, int customerId, Timestamp orderDate, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

//    Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

//    Hiển thị thông tin đơn hàng
    @Override
    public String toString() {
        return String.format("Order ID: %d | Customer ID: %d | Date: %s | Total: %.2f",
                id, customerId, orderDate.toString(), totalAmount);
    }
}
