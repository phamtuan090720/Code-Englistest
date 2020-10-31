/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HocVien;

import TracNghiem.CauHoi;
import TracNghiem.NoiDungCauHoi;
import TracNghiem.PhuongAn;
import TracNghiem.QuanLyCauHoi;
import static TracNghiem.QuanLyCauHoi.connectJDBC;
import TracNghiem.QuanLyNoiDungCauHoi;
import static TracNghiem.Ramdom.ramdom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Calendar.getInstance;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author MyPC
 */
public class HocVien {
    private String hoTen;
    private String queQuan;
    private String gioiTinh;
    private Date ngaySinh;
    private Date ngayGiaNhap;
    private int mssv;

    /**
     * Hàm Khởi Tạo 
     */
    public HocVien(int mssv,String ht, String qq,String gt , Date ns, Date ndk){
        this.hoTen = ht;
        this.mssv = mssv;
        this.ngaySinh = ns;
        this.ngayGiaNhap = null;
        this.gioiTinh = gt;
        this.queQuan = qq;
    }
    public HocVien(){}
    /**
     * Hàm hiển thị
     */
    public void hienThi(){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
       System.out.println("---------------------");
       System.out.printf("Ma So Sinh Vien : %d\nHo va ten : %s\nNgay Sinh :"
               +"%s\nQue Quan: %s\nGioi Tinh : %s\nNgay Gia Nhap : %s\n ",
               this.getMssv(),this.hoTen,f.format(this.ngaySinh),this.queQuan,
               this.gioiTinh,f.format(this.ngayGiaNhap));
    }
    /**
     * Khi Học Viên Được Tạo Hàm này có chức năng add nhứng câu hỏi có trong ngân
     * hàng đề thi vào trong phần luyện tập của học viên.
     */
    public void addDanhSachCauHoi() throws ClassNotFoundException, SQLException
    {
        List<String> ds = new ArrayList<>();
        PreparedStatement ptsm = null;
        Statement stm = null;
        stm = connectJDBC().createStatement();
        ResultSet rS = stm.executeQuery("SELECT * FROM englist_test.cauhoi");
        while(rS.next())
        {
            String id = rS.getString("id_cauhoi");
            ds.add(id);
        }
        stm.close();
        String sql = "INSERT INTO `englist_test`.`luyentap` (`id_cauhoi`, `id_mssv`)"
                + " VALUES (?, '"+this.mssv+"')";
        ptsm = connectJDBC().prepareCall(sql);
        for(int i = 0 ; i<ds.size();i++)
        {
            ptsm.setString(1,ds.get(i));
            ptsm.executeUpdate();
        }
        if(ptsm!=null)
        {
            ptsm.close();
        }
        connectJDBC().close();
    }
    /*
     * Khi câu hỏi mới được thêm vô ta sẽ gọi hàm này để add câu hỏi mới dô cho học viên.
     */
   public void updateDanhSachCauHoi(String id) throws ClassNotFoundException, SQLException
   {
       
       PreparedStatement pstm = null;
       String sql ="INSERT INTO englist_test.`luyentap`"
               + " (id_cauhoi, id_mssv, have_done) VALUES ('"+id+"', '"+this.mssv+"', '0')";
       pstm=connectJDBC().prepareCall(sql);
       pstm.executeUpdate();
       pstm.close();
       connectJDBC().close();
   }
   /**
    * Ham Nhap Hoc Vien.
    */
    public void nhap(Scanner scanner) throws ParseException
    {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Vui Long Nhap Thong Tin");
        System.out.print("Nhap Ma So Sinh Vien : ");
        this.setMssv(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Nhap Ho Ten :");
        this.hoTen = scanner.nextLine();
        System.out.print("Nhap Ngay Sinh :");
        this.ngaySinh = f.parse(scanner.nextLine());
        System.out.print("Nhap Gioi Tinh :");
        this.gioiTinh = scanner.nextLine();
        System.out.print("Nhap Que Quan :");
        this.queQuan = scanner.nextLine();
        // Lấy ngày Tháng năm hiện tại rồi gán cho this.ngayGiaNhap
        this.ngayGiaNhap = getInstance().getTime();
           
    }
    /**
     * tìm ra danh sách các câu hỏi chưa làm của học viên
     * còn thiếu truyền mssv vào hàm
     * @return Danh Sach cac cau hoi chua lam cua hoc vien 
     * @throws java.sql.SQLException 
     * @throws java.lang.ClassNotFoundException 
     */
     public List<String> dsCauHoiChuaLamMC() throws SQLException, ClassNotFoundException
     {
         List<String> ds = new ArrayList<>();
         Statement stm = null;
         stm = connectJDBC().createStatement();
         String sql ="SELECT lt.*,ch.from_cauhoi FROM cauhoi ch,luyentap lt"
                 + " where lt.id_cauhoi=ch.id_cauhoi and  have_done = '0'"
                 + " and from_cauhoi = 'Multiple Choice'"
                 + "and id_mssv='"+this.mssv+"'";
         ResultSet rs = stm.executeQuery(sql);
         while(rs.next())
         {
             ds.add(rs.getString("id_cauhoi"));
         }
         if(stm!=null)
         {
             stm.close();
         }
         return ds;
     }
     // tìm ra danh sách các câu hỏi chưa làm của dạng Incomplete và dạng Conversation
      public List<String> dsCauHoiChuaLamIPC
        (String lv,NoiDungCauHoi id,String from)
              throws SQLException, ClassNotFoundException
     {
         List<String> ds = new ArrayList<>();
         Statement stm = null;
         stm = connectJDBC().createStatement();
         String sql ="SELECT lt.* FROM luyentap lt,cauhoi ch "
                 + "where lt.id_cauhoi=ch.id_cauhoi "
                 + "and id_mssv='"+this.mssv+"'"
                 + "and have_done='0' "
                 + "and idNoiDungCauHoi ='"+id.getId_NoiDungCauHoi()+"'"
                 + "and level_cauhoi='"+lv+"'"
                 + "and from_cauhoi='"+from+"'";
         ResultSet rs = stm.executeQuery(sql);
         while(rs.next())
         {
             ds.add(rs.getString("id_cauhoi"));
         }
         if(stm!=null)
         {
             stm.close();
         }
         return ds;
     }
    public void luyenTap(Scanner scanner,HocVien hv)
            throws ClassNotFoundException, SQLException
    {
        int choice;
        do {
            System.out.println("=====category=====");
            System.out.println("1.Multiple Choice");
            System.out.println("2.Incomplete");
            System.out.println("3.Conversation");
            System.out.println("==================");

            System.out.println("Choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    hv.luyenTapMc(scanner,hv);
                    break;
                case 2:
                    System.out.println("=====Level=====");
                    System.out.println("1.Easy");
                    System.out.println("2.Difficult");
                    System.out.println("===============");
                    int choice2;
                    do {
                        System.out.print("Choice: ");
                        choice2 = scanner.nextInt();
                        switch (choice2) {
                            case 1:
                                hv.luyenTapIAndC(scanner, "Easy", "Incomplete",hv);
                                break;
                            case 2:
                                hv.luyenTapIAndC(scanner, "Difficult", "Incomplete",hv);
                                break;
                        }
                    } while (0 < choice2 && choice2 > 2);
                    break;
                case 3:
                    System.out.println("=====Level=====");
                    System.out.println("1.Easy");
                    System.out.println("2.Difficult");
                    System.out.println("===============");
                    int choice3;
                    do {
                        System.out.print("Choice: ");
                        choice3 = scanner.nextInt();
                        switch (choice3) {
                            case 1:
                                hv.luyenTapIAndC(scanner, "Easy", "Conversation",hv);
                                break;
                            case 2:
                                hv.luyenTapIAndC(scanner, "Difficult", "Conversation",hv);
                                break;
                        }
                    } while (0 < choice3 && choice3 > 2);
                    break;
            }
        } while (0 < choice && choice > 3);
    }
    /**
     * Xử Lý Phần Luyện Tập Của Dạng Câu Hỏi Multiple Choice
     */
    public void luyenTapMc(Scanner scanner, HocVien hv) throws SQLException, ClassNotFoundException {
        int diem = 0;
        List<String> arr = hv.dsCauHoiChuaLamMC();
        List<PhuongAn> dsDapAnDung = new ArrayList<>();
        List<CauHoi> dsCauHoiLuyenTap = new ArrayList<>();
        List<String> dsDapAn = new ArrayList<>();
        List<String> dsGiaiThich = new ArrayList<>();
        if (arr.isEmpty() == true) {
            System.out.println("Question list is done!");
        } else {
            // hiển thị số câu hỏi học viên chưa làm.
            System.out.println("Number of questions not yet done: " + arr.size());
            int soCau;
            do {
                System.out.print("Nhap So Cau Hoi Muon Luyen Tap: ");
                soCau = scanner.nextInt();
                if (0 < soCau && soCau > arr.size()) {
                    System.out.println
            ("Vui Long Nhap So Cau Hoi Thap Hon Hoac Bang: " + arr.size());
                }
            } while (0 < soCau && soCau > arr.size());

            ArrayList arrInt = ramdom(soCau, arr.size());

            QuanLyCauHoi qlch = new QuanLyCauHoi();
            qlch.findAllCauHoi();
            for (int i = 0; i < soCau; i++) {
                CauHoi ch = qlch.SearchQuestion(arr.get(i));
                ch.findPhuongAn();
                PhuongAn pa = ch.findDapAn();
                String giaiThich = ch.giaiThich();
                dsGiaiThich.add(giaiThich);
                dsDapAnDung.add(pa);
                int id = ch.idluyentap(hv);
                ch.haveDone(id);
                System.out.println(ch);
                System.out.print("Choice: ");
                int da = scanner.nextInt();
                dsCauHoiLuyenTap.add(ch);
                if (ch.isCheckDapAn(da) == true) {
                    String kq = "Dung";
                    dsDapAn.add(kq);
                    diem++;
                } else {
                    String kq = "Sai";
                    dsDapAn.add(kq);
                }
            }
            if (dsCauHoiLuyenTap.size() > 0) {
                for (int i = 0; i < soCau; i++) {
                    System.out.print(dsCauHoiLuyenTap.get(i));
                    System.out.println(dsDapAn.get(i));
                    System.out.printf("Dap An: %sGiai Thich: %s \n", dsDapAnDung.get(i),dsGiaiThich.get(i));
                    
                }
                 hv.insertKetQua(diem, dsCauHoiLuyenTap.size(),"Multiple Choice");
                System.out.printf(
                        "Ket Qua :%d/%d \n", diem, dsCauHoiLuyenTap.size());
            }
        }


    }
    public void luyenTapIAndC(Scanner scanner , String lv, String from,HocVien hv)
            throws ClassNotFoundException, SQLException{
        int diem = 0;
        QuanLyNoiDungCauHoi qlndch = new QuanLyNoiDungCauHoi();
        QuanLyCauHoi qlch = new QuanLyCauHoi();
        // lấy nội dung câu hỏi trong mySql
        List<NoiDungCauHoi> ds = qlndch.findNdCauHoi(from);
        // ramdom từ 0-độ dài của danh sách nội dung câu hỏi
        ArrayList arr = ramdom(1, ds.size());
        NoiDungCauHoi nd = ds.get((int) arr.get(0));
        System.out.println(nd);
        List<String> dsMaCauHoiChuaLam = hv.dsCauHoiChuaLamIPC(lv, nd, from);
        List<PhuongAn> dsDapAnDung = new ArrayList<>();
        List<CauHoi> dsCauHoiLuyenTap = new ArrayList<>();
        List<String> dsDapAn = new ArrayList<>();
        qlch.findAllCauHoi();
        if (dsMaCauHoiChuaLam.isEmpty() == false) {
            for (int i = 0; i < dsMaCauHoiChuaLam.size(); i++) {
                CauHoi h = qlch.SearchQuestion(dsMaCauHoiChuaLam.get(i));
                h.findPhuongAn();
                dsCauHoiLuyenTap.add(h);
                PhuongAn daDung = h.findDapAn();
                dsDapAnDung.add(daDung);
                int id = h.idluyentap(hv);
                h.haveDone(id);
                System.out.println(h);
                System.out.print("Choice: ");
                int da = scanner.nextInt();
                scanner.nextLine();
                if (h.isCheckDapAn(da) == true) {
                    String kq = "Dung";
                    dsDapAn.add(kq);
                    diem++;
                } else {
                    String kq = "Sai";
                    dsDapAn.add(kq);
                }
            }

            if (dsCauHoiLuyenTap.size() > 0) {
                for (int i = 0; i < dsCauHoiLuyenTap.size(); i++) {
                    System.out.println(dsCauHoiLuyenTap.get(i));
                    System.out.print(dsDapAn.get(i));
                    System.out.print("||");
                    System.out.printf("Dap An: %s", dsDapAnDung.get(i));
                }
                hv.insertKetQua(diem, dsCauHoiLuyenTap.size(), from);
                System.out.printf("Kequa : %d/%d \n", diem, dsCauHoiLuyenTap.size());
            } else {
                System.out.println("Question list is done!");
            }

        } else {
            System.out.println("Question list is done!");
        }

    }

    /**
     *Them Ket qua sau khi luyen tap.
     * @param kq
     * @param soCau
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void insertKetQua(int kq,int soCau,String from) throws SQLException, ClassNotFoundException{
        PreparedStatement pstm = null;
        String sql = "INSERT INTO englist_test.ketqua "
                + "(diem,ngayThucHien,id_mssv,soCauThucHien,fromCauHoiLuyenTap)"
                + " VALUES ('"+kq+"',?, '"+this.mssv+"','"+soCau+"','"+from+"')";
        pstm = connectJDBC().prepareCall(sql);  
        java.sql.Timestamp ngayThucHien = new java.sql.Timestamp
        (new java.util.Date().getTime());
        pstm.setTimestamp(1, ngayThucHien);
        pstm.executeUpdate();
        if(pstm!=null)
        {
            pstm.close();
        }
        connectJDBC().close();
    }
    public void thongKe(Scanner scanner) throws SQLException, ClassNotFoundException {
        Statement stm = null;
        System.out.print("Enter the month you want to statistic: ");
        int month = scanner.nextInt();
        scanner.nextLine();
        stm = connectJDBC().createStatement();
        ResultSet rs1 = stm.executeQuery("SELECT * FROM englist_test.ketqua"
                + " where month(ngayThucHien)='" + month + "'and id_mssv='"+this.mssv+"'");
        ArrayList dsIdKetQua = new ArrayList<>();
        while (rs1.next()) {
            int i = 0;
            int id = rs1.getInt("idketqua");
            dsIdKetQua.add(id);
        }
        if (dsIdKetQua.isEmpty() == true) {
            System.out.println("Ban Chua Thuc Hien Luyen Tap Lan Nao: ");
        } else {
            int diem;
            int soCauThucHien;
            ResultSet rs2 = stm.executeQuery("SELECT "
                    + "SUM(diem) diem,SUM(soCauThucHien) soCauThucHien "
                    + "FROM ketqua WHERE Month(ngayThucHien)='" + month + "'"
                            + "and id_mssv='"+this.mssv+"'");
            while (rs2.next()) {
                diem = rs2.getInt("diem");
                soCauThucHien = rs2.getInt("soCauThucHien");
                System.out.printf("So Lan Luyen Tap Trong Thang : %d \nDiem: %d/"
                        + "%d\nDiem Trung Binh Dat Duoc: %.2f\n",
                        dsIdKetQua.size(),
                        diem, soCauThucHien, ((double) diem / (double) soCauThucHien)*10);
            }
        }

        if (stm != null) {
            stm.close();
        }
        connectJDBC().close();
    }
    /**
     * @return the hoTen
     */
    public String getHoTen() {
        return hoTen;
    }

    /**
     * @param hoTen the hoTen to set
     */
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }       


    /**
     * @return the queQuan
     */
    public String getQueQuan() {
        return queQuan;
    }

    /**
     * @param queQuan the queQuan to set
     */
    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    /**
     * @return the gioiTinh
     */
    public String getGioiTinh() {
        return gioiTinh;
    }

    /**
     * @param gioiTinh the gioiTinh to set
     */
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    /**
     * @return the ngaySinh
     */
    public Date getNgaySinh() {
        return ngaySinh;
    }

    /**
     * @param ngaySinh the ngaySinh to set
     */
    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    /**
     * @return the ngayGiaNhap
     */
    public Date getNgayGiaNhap() {
        return ngayGiaNhap;
    }

    /**
     * @param ngayGiaNhap the ngayGiaNhap to set
     */
    public void setNgayGiaNhap(Date ngayGiaNhap) {
        this.ngayGiaNhap = ngayGiaNhap;
    }

    /**
     * @return the mssv
     */
    public int getMssv() {
        return mssv;
    }

    /**
     * @param mssv the mssv to set
     */
    public void setMssv(int mssv) {
        this.mssv = mssv;
    }
    
}
