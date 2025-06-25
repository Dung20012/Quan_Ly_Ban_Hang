package main;

import management.ProductManagement;

import java.util.Scanner;

public class ProductShopManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("******** MENU QUẢN LÝ SẢN PHẨM ********");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị danh sách sản phẩm");
            System.out.println("3. Cập nhật giá sản phẩm");
            System.out.println("4. Xóa sản phẩm theo ID");
            System.out.println("5. Tìm kiếm theo tên sản phẩm");
            System.out.println("6. Thống kê tổng số sản phẩm");
            System.out.println("7. Thống kê tổng tồn kho");
            System.out.println("8. Thống kê sản phẩm còn/hết hàng");
            System.out.println("9. Tìm sản phẩm giá cao nhất");
            System.out.println("10. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            }catch (Exception e){
                System.err.println("Vui lòng nhập số!");
                continue;
            }

            switch (choice){
                case 1:
                    ProductManagement.addProduct();
                    break;
                case 2:
                    ProductManagement.displayProduct();
                    break;
                case 3:
                    ProductManagement.updateProductPrice();
                    break;
                case 4:
                    ProductManagement.deleteProductsById();
                    break;
                case 5:
                    ProductManagement.searchByName();
                    break;
                case 6:
                    ProductManagement.totalProductCount();
                    break;
                case 7:
                    ProductManagement.totalStock();
                    break;
                case 8:
                    ProductManagement.countByStockstatus();
                    break;
                case 9:
                    ProductManagement.findMostExpensiveProduct();
                    break;
                case 10:
                    System.exit(0);
                default:
                    System.err.println("Vui lòng chọn từ 1 - 10");
            }
        }while (true);
    }
}
