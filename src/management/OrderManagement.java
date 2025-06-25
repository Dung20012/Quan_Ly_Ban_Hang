package management;

import entity.Customer;
import entity.OrderDetail;
import entity.Product;
import util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OrderManagement {
    private static final Scanner scanner = new Scanner(System.in);

    //    1. Thêm đơn hàng
    public static void addOrder(){
        try {
            Connection conn = Database.getConnection();
            conn.setAutoCommit(false); // bắt đầu giao dịch

//            1.1. Chọn khách hàng
            List<Customer> customers = getAllCustomers(conn);
            if (customers.isEmpty()){
                System.out.println("Không có khách hàng để tạo đơn");
                return;
            }
            customers.forEach(System.out::println);

            int customerId;
            while (true) {
                System.out.println("Nhập ID khách hàng: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.err.println("Không được để trống!");
                    continue;
                }
                try {
                    customerId = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Vui lòng nhập số nguyên!");
                }
            }

//            1.2. Chọn sản phẩm
            List<Product> products = getAllProducts(conn);
            List<OrderDetail> orderDetails = new ArrayList<>();
            double totalAmount = 0;

            while (true){
                System.out.println("Nhập ID sản phẩm (hoặc 0 để kết thúc): ");
                String productInput = scanner.nextLine().trim();
                int productId;
                try {
                    productId = Integer.parseInt(productInput);
                } catch (NumberFormatException e) {
                    System.err.println("Vui lòng nhập số nguyên!");
                    continue;
                }
                if (productId == 0){
                    break;
                }

                Optional<Product> optionalProduct = products.stream()
                        .filter(p -> p.getId() == productId)
                        .findFirst();

                if (optionalProduct.isEmpty()){
                    System.err.println("Không tìm thấy sản phẩm !");
                    continue;
                }

                Product product = optionalProduct.get();
                int quantity;
                while (true) {
                    System.out.println("Nhập số lượng: ");
                    String qtyInput = scanner.nextLine().trim();
                    try {
                        quantity = Integer.parseInt(qtyInput);
                        if (quantity <= 0 || quantity > product.getStock()) {
                            System.err.println("Số lượng không hợp lệ hoặc vượt quá tồn kho");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Vui lòng nhập số nguyên!");
                    }
                }

//                Trừ tồn kho tạm thời (chưa cập nhật DB)
                product.setStock(product.getStock() - quantity);

                OrderDetail detail = new OrderDetail(0, 0, productId, quantity, product.getPrice());
                orderDetails.add(detail);
                totalAmount += quantity * product.getPrice();
            }

            if (orderDetails.isEmpty()){
                System.out.println("Đơn hàng không có sản phẩm nào");
                return;
            }

//            1.3. Thêm vào bảng orders
            String insertOrder = "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?)";
            PreparedStatement pstmtOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            pstmtOrder.setInt(1, customerId);
            pstmtOrder.setDouble(2, totalAmount);
            pstmtOrder.executeUpdate();

            ResultSet rs = pstmtOrder.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()){
                orderId = rs.getInt(1);
            }

//            1.4 Thêm từng dòng vào bảng order_details
            String insertDetail = "INSERT INTO order_details (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmtDetail = conn.prepareStatement(insertDetail);
            for (OrderDetail d : orderDetails){
                pstmtDetail.setInt(1, orderId);
                pstmtDetail.setInt(2, d.getProductId());
                pstmtDetail.setInt(3, d.getQuantity());
                pstmtDetail.setDouble(4, d.getUnitPrice());
                pstmtDetail.addBatch();
            }
            pstmtDetail.executeBatch();

//            1.5. Cập nhật tồn kho sản phẩm
            String updateStock = "UPDATE products SET stock = ? WHERE id = ?";
            PreparedStatement pstmtStock = conn.prepareStatement(updateStock);
            for (Product p : products){
                pstmtStock.setInt(1, p.getStock());
                pstmtStock.setInt(2, p.getId());
                pstmtStock.addBatch();
            }
            pstmtStock.executeBatch();

            conn.commit(); // kết thúc giao dịch

            System.out.println("Thêm đơn hàng thành công!");
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Lỗi khi thêm đơn hàng: " + e.getMessage());
        }
    }

    //    Lấy danh sách khách hàng từ DB
    private static List<Customer> getAllCustomers(Connection conn) throws SQLException{
        List<Customer> list = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM customers");
        while (rs.next()){
            Customer c = new Customer();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phone"));
            list.add(c);
        }
        return list;
    }

    //    Lấy danh sách sản phẩm từ DB
    private static List<Product> getAllProducts(Connection conn) throws SQLException{
        List<Product> list = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM products");
        while (rs.next()){
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getDouble("price"));
            p.setStock(rs.getInt("stock"));
            list.add(p);
        }
        return list;
    }
}
