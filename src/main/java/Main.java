
import HocVien.HocVien;
import HocVien.QuanLyHocVien;
import TracNghiem.CauHoi;
import TracNghiem.QuanLyCauHoi;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MyPC
 */
public class Main {

    public static void main(String[] args)
            throws ClassNotFoundException, SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            Menu.menuDangKy();
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Main.singUp(scanner);
                    break;

                case 2:
                    Main.logIn(scanner);
                    break;
                case 3:
                    System.exit(0);
            }
        } while (0 <= choice && choice <= 3);
    }

    public Main() {
    }

    public static void singUp(Scanner scanner)
            throws ClassNotFoundException, SQLException, ParseException {
        QuanLyHocVien ql = new QuanLyHocVien();
        HocVien hv = new HocVien();
        hv.nhap(scanner);
        ql.insert(hv);
        hv.addDanhSachCauHoi();
    }

    public static void deleteStudent(Scanner scanner, HocVien hv)
            throws ClassNotFoundException, SQLException {
        System.out.println("Ban Phai Dang Nhap User De Delete");
        System.out.print("username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        String s1 = "root";
        String s2 = "123456789";
        if (s1.equals(user) == true && s2.equals(pass) == true) {
            QuanLyHocVien ql = new QuanLyHocVien();
            System.out.print("Nhap Ma So Sinh Vien Ban Muon Xoa: ");
            int mssv = scanner.nextInt();
            ql.delete(mssv);
            if (mssv == hv.getMssv()) {
                System.exit(0);
            }
        } else {
            System.out.println("Log in Failed");
        }

    }

    public static void displayTheStudentList()
            throws ClassNotFoundException, SQLException {
        QuanLyHocVien ql = new QuanLyHocVien();
        ql.findAll();
        System.out.println("====Danh Sach Hoc Vien Da Dang Ky====");
        ql.hienThiDs();
        System.out.println("=====================================");
    }

    public static void updateStudentInformation(HocVien hv)
            throws ClassNotFoundException, ParseException, SQLException {
        QuanLyHocVien qlhv = new QuanLyHocVien();
        qlhv.update(hv);
    }

    public static void searchStudentByName(Scanner scanner)
            throws ClassNotFoundException, SQLException {
        QuanLyHocVien ql = new QuanLyHocVien();
        ql.findAll();
        System.out.print("Nhap ho ten ban muon tim ");
        String x = scanner.nextLine();
        List<HocVien> ds = ql.SearchStudentByFullname(x);
        if (ds.isEmpty() == true) {
            System.out.println("Does not exist student");
        }
        ds.forEach((HocVien hv) -> {
            hv.hienThi();
        });
    }

    public static void searchStudentByBirth(Scanner scanner)
            throws ClassNotFoundException, SQLException {
        QuanLyHocVien ql = new QuanLyHocVien();
        ql.searchDate(scanner);
    }

    public static void searchStudentBySex(Scanner scanner) throws ClassNotFoundException, SQLException {
        QuanLyHocVien ql = new QuanLyHocVien();
        ql.findAll();
        System.out.print("Nhap Gioi Tinh Ban Muon Tim ");
        String x2 = scanner.nextLine();
        List<HocVien> ds = ql.SearchStudentBySex(x2);
        if (ds.isEmpty() == true) {
            System.out.println("Does not exist student");
        }
        ds.forEach((HocVien hv) -> {
            hv.hienThi();
        });
    }

    public static void searchStudentByAddress(Scanner scanner) throws ClassNotFoundException, SQLException {
        QuanLyHocVien ql = new QuanLyHocVien();
        ql.findAll();
        System.out.print("Nhap Dia Chi Ban Muon Tim ");
        String x = scanner.nextLine();
        List<HocVien> ds = ql.SearchStudentByAddress(x);
        if (ds.isEmpty() == true) {
            System.out.println("Does not exist student");
        }
        ds.forEach((HocVien hv) -> {
            hv.hienThi();
        });
    }

    public static void searchStudent(Scanner scanner)
            throws ClassNotFoundException, SQLException {
        int choice;
        QuanLyHocVien ql = new QuanLyHocVien();
        do {
            Menu.menuSeachStudent();
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    Main.searchStudentByName(scanner);
                    break;
                case 2:
                    Main.searchStudentByBirth(scanner);
                    break;
                case 3:
                    Main.searchStudentBySex(scanner);
                    break;
                case 4:
                    Main.searchStudentByAddress(scanner);
                    break;
                case 5:
                    return;

            }

        } while (0 <= choice && choice <= 5);
    }

    public static void searchQuestionByContent(Scanner scanner) throws SQLException, ClassNotFoundException {
        QuanLyCauHoi qlch = new QuanLyCauHoi();
        qlch.findAllCauHoi();
        System.out.print("Enter the content to search: ");
        String keyword = scanner.nextLine();
        List<CauHoi> dsCauHoi = qlch.SearchQuestionByContent(keyword);
        if (dsCauHoi.isEmpty() == true) {
            System.out.println("Does not exist question");
        } else {
            dsCauHoi.forEach((CauHoi ch) -> {
                ch.hienThi();
            });

        }
    }

    public static void searchQuestionByCategory(Scanner scanner)
            throws SQLException, ClassNotFoundException {
        QuanLyCauHoi qlch = new QuanLyCauHoi();
        qlch.findAllCauHoi();
        System.out.print("Enter the category to search: ");
        String keyword = scanner.nextLine();
        List<CauHoi> dsCauHoi = qlch.SearchQuestionByCategory(keyword);
        if (dsCauHoi.isEmpty() == true) {
            System.out.println("Does not exist question");
        } else {
            dsCauHoi.forEach((CauHoi ch) -> {
                ch.hienThi();
            });

        }
    }

    public static void searchQuestionByLevel(Scanner scanner) throws SQLException, ClassNotFoundException {
        QuanLyCauHoi qlch = new QuanLyCauHoi();
        qlch.findAllCauHoi();
        System.out.print("Enter the level to search: ");
        String keyword = scanner.nextLine();
        List<CauHoi> dsCauHoi = qlch.SearchQuestionbyLevel(keyword);
        if (dsCauHoi.isEmpty() == true) {
            System.out.println("Does not exist question");
        } else {
            dsCauHoi.forEach((CauHoi ch) -> {
                ch.hienThi();
            });

        }
    }

    public static void searchQuestion(Scanner scanner) throws SQLException, ClassNotFoundException {
        int choice;
        do {
            Menu.QuanLyCauHoi();
            System.out.print("Choice :");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    Main.searchQuestionByContent(scanner);
                    break;
                case 2:
                    Main.searchQuestionByCategory(scanner);
                    break;
                case 3:
                    Main.searchQuestionByLevel(scanner);
                    break;
                case 4:
                    return;
            }
        } while (0 <= choice && choice <= 4);
    }

    public static void logIn(Scanner scanner)
            throws ClassNotFoundException, SQLException, ParseException {
        QuanLyHocVien ql = new QuanLyHocVien();
        System.out.print("Enter the student ID : ");
        int mssv = scanner.nextInt();
        scanner.nextLine();
        ql.findAll();

        if (ql.istimKiemMssv(mssv) == false) {
            System.out.println("Student ID has not been registered!");
        } else {
            HocVien hv = ql.timKiemMssv(mssv);
            Menu.menuQuanLyHocVien();
            int choice;
            do {
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        Main.displayTheStudentList();
                        break;
                    case 2:
                        Main.searchStudent(scanner);
                        break;
                    case 3:
                        Main.updateStudentInformation(hv);
                        break;
                    case 4:
                        Main.deleteStudent(scanner, hv);
                        break;
                    case 5:
                        Main.searchQuestion(scanner);
                        break;
                    case 6:
                        hv.luyenTap(scanner, hv);
                        break;
                    case 7:
                        hv.thongKe(scanner);
                        break;
                    case 8:
                        return;
                }
                Menu.menuQuanLyHocVien();
            } while (0 <= choice && choice <= 7);
        }
    }
}
