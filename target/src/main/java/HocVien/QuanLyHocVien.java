/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HocVien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MyPC
 */
public class QuanLyHocVien {
    public static Connection connectJDBC() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/englist_test", "root", "123456789");
        return conn;
    }
    private List<HocVien> ds = new ArrayList<>();
    public void hienThiDs(){
        // Kiem Tra xem trong danh sách có chứ học viên nào không nếu không trả về true và ngược lại
        if(this.ds.isEmpty()==true)
        {
            System.out.println("Does not exist student");
        }
        this.ds.forEach((HocVien hv) ->{
            hv.hienThi();
        });
    }
    public void them(HocVien hv){
        this.ds.add(hv);
    }
    /**
     * Lấy Thông Tin Trong My SQL rồi Thêm Vào Trong this.ds List<HocVien> 
     */
    public void findAll() throws ClassNotFoundException, SQLException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Statement stm = null;
            stm = connectJDBC().createStatement();
            ResultSet rS = stm.executeQuery("SELECT * FROM englist_test.hocvien");
            while(rS.next())
            {
                HocVien hv = new HocVien();
                hv.setMssv(rS.getInt("mssv_id"));
                hv.setHoTen(rS.getString("hoTen"));
                hv.setQueQuan(rS.getString("queQuan"));
                hv.setGioiTinh(rS.getString("gioiTinh"));
                hv.setNgaySinh(rS.getDate("ngaySinh"));
                hv.setNgayGiaNhap(rS.getDate("ngayGiaNhap"));
                this.ds.add(hv);
            }
            if(stm!=null)
            {
                stm.close();
            }
            connectJDBC().close();
    }
    /**
     * Hàm chèn Thông tin học viên vào SQL
     */
    public void insert(HocVien hv) throws ClassNotFoundException, SQLException
    {
        PreparedStatement pstm = null;
            String sql = "INSERT INTO englist_test.hocvien (mssv_id, hoTen, queQuan,gioiTinh,ngaySinh,ngayGiaNhap)"
                            + " VALUES (?,?,?,?,?,?)";
            pstm = connectJDBC().prepareCall(sql);
            pstm.setInt(1,hv.getMssv());
            pstm.setString(2,hv.getHoTen());
            pstm.setString(3,hv.getQueQuan());
            pstm.setString(4,hv.getGioiTinh());
            pstm.setDate(5, new java.sql.Date(hv.getNgaySinh().getTime()));
            pstm.setDate(6, new java.sql.Date(hv.getNgayGiaNhap().getTime()));
            pstm.executeUpdate();
            if(pstm!=null)
            {
                System.out.println("Insert Successful");
                pstm.close();
            }
            connectJDBC().close();
    }
     /**
     * Hàm cập nhật thông tin người học dựa vào mssv cua hoc vien
     */
      public void update(HocVien hv) throws ClassNotFoundException, ParseException, SQLException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Scanner scanner = new Scanner(System.in);
        PreparedStatement pstm = null;
        String sql = "UPDATE englist_test.hocvien SET hoTen=?,queQuan=?,gioiTinh=?,ngaySinh=?"
                + "WHERE mssv_id='" + hv.getMssv() + "'";
        pstm = connectJDBC().prepareCall(sql);
        System.out.print("Nhap Ho Ten:");
        String hoten = scanner.nextLine();
        System.out.print("Nhap Ngay Sinh:");
        Date ngaySinh = f.parse(scanner.nextLine());
        System.out.print("Nhap Gioi Tinh:");
        String gioiTinh = scanner.nextLine();
        System.out.print("Nhap Que Quan:");
        String queQuan = scanner.nextLine();
        pstm.setString(1, hoten);
        pstm.setString(2, queQuan);
        pstm.setString(3, gioiTinh);
        pstm.setDate(4, new java.sql.Date(ngaySinh.getTime()));
        int k = pstm.executeUpdate();
        if (k > 0) {
            System.out.println("Update Susscesful");
        } else {
            System.out.println("Update Failed");
        }
        if (pstm != null) {
            pstm.close();
        }
        connectJDBC().close();
    }
  
    /**
     * Hàm Xóa Thông Tin Người Học nhap vao thong tin nguoi hoc sau do xoa thong
     * tin them ma so sinh vien.
     */
    public void delete(int ms) throws ClassNotFoundException, SQLException
    {
        PreparedStatement pstm = null;
        String sql = "DELETE FROM englist_test.hocvien WHERE (mssv_id = ?)";
        pstm = connectJDBC().prepareCall(sql);
        pstm.setInt(1, ms);
        int k = pstm.executeUpdate();
        if (k > 0) {
            System.out.println("Delete Successful");
        } else {
            System.out.println("Delete Failed");
        }
        pstm.close();
        connectJDBC().close();
    }
    public HocVien timKiemMssv(int x)
    {
        for(HocVien h : this.ds)
        {
            if(h.getMssv()== x)
                return h;
        }
        return null;
    }
    /**
     * Ham Tim Kiem Theo mssv
     * nhập vào mssv sau đó return học viên cần tìm
     */
    public boolean istimKiemMssv(int x)
    {
        for(HocVien h : this.ds)
        {
            if(h.getMssv()== x)
                return true;
        }
        return false;
    }
    /**
     * Ham Tim Kiem Theo Ngay Thang Nam Sinh
     * Truyền vào ngày tháng năm để tìm kiếm
     * @param d : Day
     * @param m : Month
     * @param y : Year
     */
    public void searchDate(Scanner scanner) throws ClassNotFoundException, SQLException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Statement stm = null;
        System.out.println("Nhap Ngay Thang Nam Sinh Ban Muon Tim:");
        System.out.print("Day:");
        byte d = scanner.nextByte();
        scanner.nextLine();
        System.out.print("Month:");
        byte m = scanner.nextByte();
        scanner.nextLine();
        System.out.print("Year:");
        int y = scanner.nextInt();
        scanner.nextLine();
        String sql = "select*from englist_test.hocvien "
                + "where day(ngaySinh)='" + d + "'"
                + "and month(ngaySinh)='" + m + "'"
                + " and year(ngaySinh) = '" + y + "' ";
        stm = connectJDBC().prepareCall(sql);
        ResultSet rS = stm.executeQuery(sql);
        while (rS.next()) {
            HocVien hv = new HocVien();
            hv.setMssv(rS.getInt("mssv_id"));
            hv.setHoTen(rS.getString("hoTen"));
            hv.setGioiTinh(rS.getString("gioiTinh"));
            hv.setQueQuan(rS.getString("queQuan"));
            hv.setNgaySinh(rS.getDate("ngaySinh"));
            hv.setNgayGiaNhap(rS.getDate("ngayGiaNhap"));
            this.ds.add(hv);
        }
        if (stm != null) {
            stm.close();
            if (this.ds.isEmpty() == true) {
                System.out.println("Does not exist student");
            } else {
                this.ds.forEach((HocVien hv) -> {
                    hv.hienThi();
                });
            }
       
             
        }
        connectJDBC().close();
    }
    public List<HocVien> SearchStudentByFullname(String keyword)
    {
        List<HocVien> kq = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for(HocVien hv : this.ds)
        {
            if(hv.getHoTen().toLowerCase().contains(keyword)==true)
            {
                kq.add(hv);
            }
        }
        return kq;
    }
    public List<HocVien> SearchStudentBySex(String keyword)
    {
        List<HocVien> kq = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for(HocVien hv : this.ds)
        {
            if(hv.getGioiTinh().toLowerCase().contains(keyword)==true)
            {
                kq.add(hv);
            }
        }
        return kq;
    }
     public List<HocVien> SearchStudentByAddress(String keyword)
    {
        List<HocVien> kq = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for(HocVien hv : this.ds)
        {
            if(hv.getQueQuan().toLowerCase().contains(keyword)==true)
            {
                kq.add(hv);
            }
        }
        return kq;
    }
    /**
     * Tìm Kiếm Học Viên Theo Keyword 
     */
    public List<HocVien> searchKeyword(String keyword)
    {
        List<HocVien> kq = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for(HocVien h : this.ds)
        {
            if(h.getHoTen().toLowerCase().contains(keyword)==true ||
                    h.getQueQuan().toLowerCase().contains(keyword) == true
                 || h.getGioiTinh().toLowerCase().contains(keyword) == true)
            {
                kq.add(h);
            }
        }
        return kq;
    }
    /**
     * @return the ds
     */
    public List<HocVien> getDs() {
        return ds;
    }

    /**
     * @param ds the ds to set
     */
    public void setDs(List<HocVien> ds) {
        this.ds = ds;
    }
    
}
