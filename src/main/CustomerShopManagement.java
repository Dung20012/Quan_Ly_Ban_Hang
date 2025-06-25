package main;

import management.CustomerManagement;

import java.util.Scanner;

public class CustomerShopManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("******** QUẢN LÝ KHÁCH HÀNG ********");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Hiển thị danh sách khách hàng");
            System.out.println("3. Tìm kiếm khách hàng theo tên");
            System.out.println("4. Xóa khách hàng theo ID");
            System.out.println("5. Cập nhật thông tin khách hàng");
            System.out.println("6. Thoát");
            System.out.println("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            }catch (Exception e){
                System.err.println("Vui lòng nhập số !");
                continue;
            }

            switch (choice){
                case 1:
                    CustomerManagement.addCustomer();
                    break;
                case 2:
                    CustomerManagement.displayCustomers();
                    break;
                case 3:
                    CustomerManagement.searchByName();
                    break;
                case 4:
                    CustomerManagement.deleteCustomerById();
                    break;
                case 5:
                    CustomerManagement.updateCustomerById();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1 - 6");
            }
        }while (true);
    }
}
