/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TracNghiem;

import HocVien.HocVien;
import HocVien.QuanLyHocVien;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MyPC
 */
public class CauHoi {

    
    private String maCauHoi;
    private List<PhuongAn>phuongAn = new ArrayList<>();
    private String noiDung;
    private Level level;
    private DanhMuc danhMuc;
    private PhuongAn dapAn;
    public static Connection connectJDBC() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/englist_test", "root", "123456789");
        return conn;
    }
    
    /**
     * Khoi  Tao  NoiDung
     */
    public CauHoi(){
    }
    //,String mch,Level level,DanhMuc danhMuc
    public CauHoi(String mch,DanhMuc from,Level lv,String nd)
    {
        this.noiDung = nd;
        this.danhMuc = from;
        this.maCauHoi = mch;
        this.level = lv;
    }
    /**
     * Tim Tat Ca Phuong An tuong ung voi macauhoi cua cau hoi roi sau do them 
     * dap an vao trong danh sach.
     */
    public void findPhuongAn() throws ClassNotFoundException, SQLException
    {
        Statement stm = null;
            stm = connectJDBC().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM englist_test.phuongan"
                    + " where id_cauhoi = '"+this.maCauHoi+"' ");
            while(rs.next())
            {
                PhuongAn pa = new PhuongAn();
                pa.setDung(rs.getBoolean("dapAn"));
                pa.setNoiDung(rs.getString("noiDung"));
                this.phuongAn.add(pa);
            }
            if(stm!=null)
            {
                stm.close();
            }
            connectJDBC().close();
    }
    public  PhuongAn findDapAn () throws SQLException, ClassNotFoundException{
        PhuongAn pa = new PhuongAn();
        Statement stm = null;
        stm = connectJDBC().createStatement();
        ResultSet rS = stm.executeQuery("SELECT * FROM englist_test.phuongan"
                + " where id_cauhoi = '"+this.maCauHoi+"'and dapAn = '1'" );
        while(rS.next())
        {
            pa.setNoiDung(rS.getString("noiDung"));
        }
        connectJDBC().close();
        stm.close();
        return pa; 
    }
    /**
     * Ham check xem phuong an dung. 
     */
    public boolean isCheckDapAn(int da){
        int a = da-1;
        for(int i=0;i<this.phuongAn.size();i++)
        {
                if(this.phuongAn.get(i).isDung()== true && (i) == a)
                return true;
        }
        return false;
    }
    public void hienThi()
    {
        System.out.printf("ID Cau Hoi :%s, "
                + "Noi Dung Cau Hoi:%s, Level: %s, Danh Muc:%s"
                ,this.maCauHoi,this.noiDung,this.level,this.danhMuc);
        System.out.println();
    }
    /**
     * Lay id luyen tap
     * @return int id;
     */
    public int idluyentap(HocVien hv) throws SQLException, ClassNotFoundException
    {
         Statement stm = null;
        stm = connectJDBC().createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM englist_test.luyentap"
                + " where id_mssv ='"+hv.getMssv()+"'and id_cauhoi='"+this.maCauHoi+"'");
        while(rs.next())
        {
             int id = rs.getInt("idluyentap");
             return id;
        }
        if(stm!=null)
        {
            stm.close();
        }
        connectJDBC().close();
        return 0;
    }
     public void haveDone(int id) throws SQLException, ClassNotFoundException{
        PreparedStatement pstm = null;
            String sql = "UPDATE englist_test.luyentap SET have_done = '1' WHERE (idluyentap = '"+id+"')";
             pstm = connectJDBC().prepareCall(sql);
             pstm.executeUpdate();

         if (pstm != null) {
             pstm.close();
         }

        connectJDBC().close();
     }
     public String giaiThich() throws SQLException, ClassNotFoundException{
         Statement stm = null;
         stm = connectJDBC().createStatement();
         String sql = "SELECT * FROM englist_test.phuongan "
                 + "where id_cauhoi='"+this.maCauHoi+"' and dapAn = '1'";
         ResultSet rs = stm.executeQuery(sql);
         while(rs.next())
         {
             String giaiThich = rs.getString("giaiThich");
             return giaiThich;
         }
         if(stm!=null)
         {
             stm.close();
         }
         connectJDBC().close();
         return null;
     }
    @Override
    public String toString() {
        
        String s = String.format("%s\n", this.noiDung);
        for(int i=0;i<this.phuongAn.size();i++)
        {
            
            s += String.format("%d.%s",i+1, this.phuongAn.get(i));
         
        }
            return s;
    }
    public void insertDapAn(PhuongAn pa,Scanner scanner) 
            throws SQLException, ClassNotFoundException
    {
        PreparedStatement pstm = null;
        if(pa.isDung()==true)
        {
             String sql = "INSERT INTO englist_test.phuongan"
                + " (id_cauhoi, dapAn, noiDung,giaiThich) VALUES ('"+this.maCauHoi+"'"
                + ",'1', '"+pa.getNoiDung()+"','"+pa.getGiaiThich()+"')";
              pstm=connectJDBC().prepareCall(sql);
        pstm.executeUpdate();
        }
        else{
              String sql = "INSERT INTO englist_test.phuongan"
                + " (id_cauhoi, dapAn, noiDung,giaiThich) VALUES ('"+this.maCauHoi+"'"
                + ",'0', '"+pa.getNoiDung()+"','"+pa.getGiaiThich()+"')";
               pstm=connectJDBC().prepareCall(sql);
        pstm.executeUpdate();
        }
       
       
        if(pstm!=null)
        {
            pstm.close();
        }
        connectJDBC().close();
    }    
    
    public void insertCauHoi(Scanner scanner) throws
            SQLException, ClassNotFoundException
    {
        QuanLyHocVien ql = new QuanLyHocVien();
        ql.findAll();
        PhuongAn pa = new PhuongAn();
        DanhMuc dm = new DanhMuc();
        Level lv = new Level();
        System.out.print("Nhap Noi Dung ");
        this.noiDung = scanner.nextLine(); 
        System.out.print("Nhap Level ");
        lv.nhap(scanner);
        this.level = lv;
        System.out.print("Nhap Danh Muc");
        dm.Nhap(scanner);
        this.danhMuc = dm;
        this.maCauHoi=createIdCauHoi(this.danhMuc);
        PreparedStatement pstm = null;
        String sql = "INSERT INTO englist_test.cauhoi"
                + " (id_cauhoi, from_cauhoi, level_cauhoi, noiDungcauhoi"
                + ") VALUES ('"+this.maCauHoi+"'"
                + ", '"+this.danhMuc+"', '"+this.level+"', '"+this.noiDung+"')";
        pstm=connectJDBC().prepareCall(sql);
        pstm.executeUpdate();
        if(pstm!=null)
        {
            pstm.close();
        }
        connectJDBC().close();

        List<PhuongAn> dsPhuongAn = new ArrayList<>();
        int n;
        do {
           
            
            System.out.print("Nhap so phuong an :");
            n = scanner.nextInt();
            scanner.nextLine();
            if(n>=2)
            {
                for (int i = 0; i < n; i++) {
                pa.nhapPhuongAn(scanner);
                pa.nhapGiaiThich(scanner);
                dsPhuongAn.add(pa);
                this.insertDapAn(pa, scanner);
            }
            }
           
            if(0<=n&&n<2)
            {
                System.out.println("Cau Hoi Phai Co 2 Phuong An Tro Len:");
            }
        }while(0<=n&&n<2);
        
            for(HocVien hv : ql.getDs())
            {
                hv.updateDanhSachCauHoi(this.maCauHoi);
            }
    }
    /**
     * Ham tao id cau hoi.
     */
    public static String createIdCauHoi(DanhMuc from) throws SQLException, ClassNotFoundException
    {
        QuanLyCauHoi ql = new QuanLyCauHoi();
        ql.findAllCauHoi();
        List<CauHoi>ds=ql.SearchQuestionByCategory(from.getDanhMuc());
        int dem = ds.size()+1 ;
        String id_cauhoi= String.format("%s%d","MC",dem);
        return id_cauhoi;
    }
    /**
     * @return the maCauHoi
     */
    public String getMaCauHoi() {
        return maCauHoi;
    }

    /**
     * @param maCauHoi the maCauHoi to set
     */
    public void setMaCauHoi(String maCauHoi) {
        this.maCauHoi = maCauHoi;
    }

    /**
     * @return the noiDung
     */
    public String getNoiDung() {
        return noiDung;
    }

    /**
     * @param noiDung the noiDung to set
     */
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    /**
     * @return the phuongAn
     */
    public List<PhuongAn> getPhuongAn() {
        return phuongAn;
    }

    /**
     * @param phuongAn the phuongAn to set
     */
    public void setPhuongAn(List<PhuongAn> phuongAn) {
        this.phuongAn = phuongAn;
    }

    /**
     * @return the level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * @return the danhMuc
     */
    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    /**
     * @param danhMuc the danhMuc to set
     */
    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }
}
