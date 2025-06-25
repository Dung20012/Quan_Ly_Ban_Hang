package entity;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;

//    Constructor không tham số
    public Customer() {
    }
//    Constructor đầy đủ tham số
    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    Hiển thị thông tin khách hàng
    @Override
    public String toString() {
        return String.format("ID: %d  | Name: %s | Email: %s | Phone: %s",
                id, name, email, phone);
    }
}
