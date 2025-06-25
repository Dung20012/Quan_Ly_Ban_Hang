# Quản Lý Bán Hàng - Java Console App

Ứng dụng Java Console mô phỏng hệ thống quản lý bán hàng, sử dụng **OOP + JDBC + MySQL**.
---

## 🛠️ Công nghệ sử dụng

* Java 8+
* JDBC API
* MySQL 8.x
* SQL Transaction
* Java Console
* OOP (Entity, Management tách riêng)

---

### 📦 Quản lý sản phẩm

* Thêm / sửa giá / xóa sản phẩm
* Tìm kiếm theo tên
* Thống kê tồn kho, giá cao nhất

### 👥 Quản lý khách hàng

* Thêm khách hàng (validate email, SĐT)
* Cập nhật, tìm kiếm, xóa khách hàng

### 🧾 Quản lý đơn hàng

* Tạo đơn hàng: chọn khách hàng + nhiều sản phẩm
* Tính tổng tiền, ghi vào 2 bảng `orders`, `order_details`
* Cập nhật tồn kho, sử dụng Transaction để đảm bảo toàn vẹn

---

## ⚙️ Cấu trúc thư mục

```
src/
├── entity/           // Các class dữ liệu (Product, Customer, Order...)
├── management/       // Xử lý chức năng nghiệp vụ
├── util/             // Kết nối CSDL (Database.java)
└── main/             // Chương trình chính (OrderShopManagement.java)
```

---

## 🗃 Cấu trúc CSDL (MySQL)

```sql
CREATE DATABASE Quan_ly_ban_hang;
USE Quan_ly_ban_hang;

CREATE TABLE products (...);
CREATE TABLE customers (...);
CREATE TABLE orders (...);
CREATE TABLE order_details (...);
```

> File SQL chi tiết nằm trong `Quan_ly_ban_hang.sql`

---

## ▶️ Hướng dẫn chạy

1. Import file SQL vào MySQL
2. Cập nhật kết nối DB trong `Database.java`
3. Biên dịch và chạy `OrderShopManagement.java`
4. Làm việc qua giao diện menu Console

---

## 📌 Tác giả

* 👤 **Dũng**
* 💼 GitHub: [github.com/Dung](https://github.com/Dung20012/Quan_Ly_Ban_Hang)
