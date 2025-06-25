package management;

import entity.Customer;
import util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CustomerManagement {
    private static final Scanner scanner = new Scanner(System.in);

//    Lấy danh sách khách hàng
    private static List<Customer> gettAllCustomers(){
        List<Customer> list = new ArrayList<>();
        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT * FROM customers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                list.add(c);
            }
        }catch (Exception e){
            System.err.println("Lỗi khi truy vấn: " + e.getMessage());
        }
        return list;
    }

//    1. Thêm khách hàng
    public static void addCustomer(){
        try {
            Connection conn = Database.getConnection();
            String name, email, phone;

//            Nhập tên
            while (true){
                System.out.println("Nhập tên khác hàng: ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()){
                    break;
                }
                System.err.println("Tên không được để trống !");
            }

//            Nhập email
            while (true){
                System.out.println("Nhập email: ");
                email = scanner.nextLine().trim();
                if (!email.isEmpty() && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")){
                    break;
                }
                System.err.println("Email không hợp lệ !");
            }

//            Nhập SĐT và ktra trùng
            while (true) {
                System.out.println("Nhập số điện thoại: ");
                phone = scanner.nextLine().trim();
                if (phone.isEmpty() || !phone.matches("^(0|84)(3[2-9]|5[6|8|9]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$")) {
                    System.err.println("Số điện thoại không hợp lệ!");
                    continue;
                }
                if (isPhoneExists(phone)) {
                    System.err.println("Số điện thoại đã tồn tại !");
                    continue;
                }
                break;
            }

            String sql = "INSERT INTO customers(name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Thêm khách hàng thành công!" : " Thêm khách hàng thất bại");
        }catch (SQLException e){
            if (e.getMessage().contains("Duplicate entry")){
                System.err.println("Email này đã tồn tại !");
            }else {
                System.err.println("Lỗi: " +  e.getMessage());
            }
        }
    }

//    2. Hiển thị danh sách khách hàng
    public static void displayCustomers(){
        List<Customer> list = gettAllCustomers();
        if (list.isEmpty()){
            System.out.println("Danh sách khách hàng rỗng");
        }else {
            System.out.println("--- DANH SÁCH KHÁCH HÀNG ---");
            list.forEach(System.out::println);
        }
    }

//    3. Tìm kiếm khách hàng theo tên khách hàng
    public static void searchByName(){
        System.out.println("Nhập tên khách hàng cần tìm: ");
        String name = scanner.nextLine().trim();

        List<Customer> result = gettAllCustomers().stream()
                .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (result.isEmpty()){
            System.err.println("Không tìm thấy khách hàng");
        }else {
            System.out.println("Kết quả tìm kiếm: ");
            result.forEach(System.out::println);
        }
    }

//    4. Xóa khách hàng theo ID
    public static void deleteCustomerById(){
        System.out.println("Nhập ID khách hàng cần xóa: ");
        String input = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(input);
        }catch (Exception e){
            System.err.println("ID không hợp lệ !");
            return;
        }

        try {
            Connection conn = Database.getConnection();
            String sql = "DELETE FROm customers WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0){
                System.out.println("Đã xóa khách hàng có ID: " + id);
            }else {
                System.err.println("Không tìm thấy khách hàng có ID: " + id);
            }
        }catch (SQLException e){
            System.err.println("Lỗi khi xóa khách hàng: " + e.getMessage());
        }
    }

//    5. Cập nhật thông tin khách hàng
    public static void updateCustomerById(){
        System.out.println("Nhập ID khách hàng cần cập nhật: ");
        String input = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(input);
        }catch (Exception e){
            System.err.println("ID không hợp lệ !");
            return;
        }

        try {
            Connection conn = Database.getConnection();
//            Ktra khách hàng tồn tại hay chưa?
            String checkSql = "SELECT * FROM customers WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()){
                System.err.println("Không tìm thấy khách hàng với ID: " + id);
                return;
            }

            System.out.println("Nhập tên mới (bỏ trống để giữ nguyên): ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()){
                name = rs.getString("name");
            }
            System.out.println("Nhập email mới (bỏ trống để giữ nguyên): ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()){
                email = rs.getString("email");
            }
            System.out.println("Nhập số điện thoại mới (bỏ trống để giữ nguyên): ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()){
                phone = rs.getString("phone");
            }

            String updateSql = "UPDATE customers SET name = ?, email = ?, phone = ? WHERE id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, name);
            updateStmt.setString(2, email);
            updateStmt.setString(3, phone);
            updateStmt.setInt(4, id);

            int rows = updateStmt.executeUpdate();
            if (rows > 0 ){
                System.out.println("Đã cập nhật thông tin khách hàng");
            }else {
                System.err.println("Cập nhật thất bại");
            }
        }catch (SQLException e){
            System.err.println("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

//    Kiểm tra SĐT tồn tại hay chưa?
    private static boolean isPhoneExists(String phone){
        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT id FROM customers WHERE phone = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // nếu có kết quả trả về -> trùng sđt
        }catch (SQLException e){
            System.err.println("Lỗi khi kiểm tra số điện thoại: " + e.getMessage());
            return false;
        }
    }
}
