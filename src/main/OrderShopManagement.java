package main;

import management.CustomerManagement;
import management.OrderManagement;
import management.ProductManagement;

import java.util.Scanner;

public class OrderShopManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("*** MENU ***");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Thêm đơn hàng");
            System.out.println("4. Thoát");
            System.out.println("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            }catch (Exception e){
                System.err.println("Vui lòng nhập số !");
                continue;
            }

            switch (choice){
                case 1:
//                    Gọi menu sản phẩm
                    ProductManagement.displayProduct();
                    break;
                case 2:
//                    Gọi menu khách hàng
                    CustomerManagement.displayCustomers();
                    break;
                case 3:
                    OrderManagement.addOrder();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1 - 4");
            }
        }while (true);
    }
}
