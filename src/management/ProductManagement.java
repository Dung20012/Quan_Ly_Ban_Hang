package management;

import entity.Product;
import util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class ProductManagement {
    private static final Scanner scanner = new Scanner(System.in);

    //    1. Thêm sản phẩm mới
    public static void addProduct(){
        try {
            Connection conn = Database.getConnection();
            String name;
            while (true){
                System.out.println("Nhập tên sản phẩm: ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    break;
                }
                System.err.println("Vui lòng không để trống!");
            }

            double price;
            while (true){
                System.out.println("Nhập giá sản phẩm: ");
                String input = scanner.nextLine();
                if (input.isEmpty()){
                    System.err.println("Giá sản phẩm không được để trống!");
                    continue;
                }
                try {
                    price = Double.parseDouble(input);
                    if (price > 0){
                        break;
                    }
                    System.err.println("Giá sản phẩm phải > 0");
                }catch (Exception e){
                    System.err.println("Vu lòng nhập đúng định dạng số!");
                }
            }

            int stock;
            while (true){
                System.out.println("Nhập số lượng tồn kho: ");
                String input = scanner.nextLine().trim();
                try {
                    stock = Integer.parseInt(input);
                    if (stock >= 0){
                        break;
                    }
                    System.err.println("Số lượng tồn kho phải >= 0!");
                }catch (Exception e){
                    System.err.println("Vui lòng nhập số nguyên!");
                }
            }

            String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, stock);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Thêm sản phẩm thành công!" : "Thêm sản phẩm thất bại!!!");
        }catch (Exception e){
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

//    2. Hiển thị danh sách sản phẩm
    public static void displayProduct(){
        List<Product> products = getAllProducts();
        if (products.isEmpty()){
            System.out.println("Không có sản phẩm nào");
        }else {
            products.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .forEach(System.out::println);
        }
    }

//    3. Cập nhật giá sản phẩm theo ID
public static void updateProductPrice() {
    System.out.println("Nhập ID sản phẩm cần cập nhật: ");
    int id;

    while (true) {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.err.println("ID sản phẩm không được để trống. Nhập lại!");
            continue;
        }
        try {
            id = Integer.parseInt(input);
            break;
        } catch (Exception e) {
            System.err.println("Vui lòng nhập số nguyên hợp lệ!");
        }
    }

    double newPrice;
    while (true) {
        System.out.println("Nhập giá mới cho sản phẩm:");
        String priceInput = scanner.nextLine();
        if (priceInput.isEmpty()) {
            System.err.println("Giá không được để trống!");
            continue;
        }
        try {
            newPrice = Double.parseDouble(priceInput);
            if (newPrice > 0) break;
            System.err.println("Giá phải > 0!");
        } catch (Exception e) {
            System.err.println("Giá không hợp lệ!");
        }
    }

    try {
        Connection conn = Database.getConnection();
        String sql = "UPDATE products SET price = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDouble(1, newPrice);
        pstmt.setInt(2, id);

        int rows = pstmt.executeUpdate();
        System.out.println(rows > 0 ? "Cập nhật thành công!" : "Không tìm thấy sản phẩm có ID: " + id);
    } catch (Exception e) {
        System.err.println("Lỗi: " + e.getMessage());
    }
}


    //    4. Xóa sản phẩm theo id
    public static void deleteProductsById() {
        int id;
        while (true) {
            System.out.println("Nhập ID sản phẩm cần xóa: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.err.println("ID không được để trống. Nhập lại!");
                continue;
            }
            try {
                id = Integer.parseInt(input);
                break;
            } catch (Exception e) {
                System.err.println("Vui lòng nhập số nguyên hợp lệ");
            }
        }

        try {
            Connection conn = Database.getConnection();
            String sql = "DELETE FROM products WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? " Đã xóa sản phẩm " : " Không tìm thấy sản phẩm có ID " + id);
        } catch (Exception e) {
            System.err.println("Lỗi " + e.getMessage());
        }
    }


//    5. Tìm kiếm sản phẩm theo tên (không phần biệt hoa thường)
    public static void searchByName(){
        String name;

        while (true){
            System.out.println("Nhập tên sản phẩm cần tìm: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()){
                break;
            }
            System.err.println("Tên sản phẩm không được để trống!");
        }

        String finalName = name;
        List<Product> list = getAllProducts().stream()
                .filter(product -> product.getName().toLowerCase().contains(finalName.toLowerCase()))
                .toList();

        if (list.isEmpty()){
            System.err.println("Không tìm thấy sản phẩm");
        }else {
            list.forEach(System.out::println);
        }
    }

//    6. Thống kê tổng số sản phẩm
    public static void totalProductCount(){
        List<Product> list = getAllProducts();
        System.out.println("Tổng sản phẩm: " + list.size());
    }

//    7. Tổng tồn kho
    public static void totalStock(){
        int total = getAllProducts().stream()
                .mapToInt(Product::getStock)
                .sum();
        System.out.println("Tổng số lượng tồn kho: " + total);
    }

//    8. Thống kê sản phẩm theo kho: hết hàng / còn hàng
    public static void countByStockstatus(){
        Map<String, Long> status = getAllProducts().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getStock() > 0 ? " Còn hàng " : " Hết hàng",
                        Collectors.counting()
                ));
        status.forEach((k, v) -> System.out.println(k + " : " + v));
    }

//    9. Tìm sản phẩm có giá cao nhất
    public static void findMostExpensiveProduct(){
        Optional<Product> max = getAllProducts().stream()
                .max(Comparator.comparing(Product::getPrice));

        max.ifPresentOrElse(
                p -> System.out.println("Sản phẩm có giá cao nhất:\n" + p),
                () -> System.out.println("Không có sản phẩm nào")
        );
    }

//    Lấy danh sách từ DB
    private static List<Product> getAllProducts(){
        List<Product> list = new ArrayList<>();
        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT * FROM products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                list.add(p);
            }
        }catch (Exception e){
            System.err.println("Lỗi khi truy vấn: " + e.getMessage());
        }
        return list;
    }
}
