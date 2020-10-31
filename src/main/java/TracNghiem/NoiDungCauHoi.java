/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TracNghiem;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class NoiDungCauHoi {
    private String danhMuc;
    private String id_NoiDungCauHoi;
    private String noiDung;
    private List<CauHoi> dsCauHoi = new ArrayList<>();
    public static Connection connectJDBC() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/englist_test", "root", "123456789");
        return conn;
    }
    public NoiDungCauHoi(){
    }
    public NoiDungCauHoi(String id,String nd){
        this.noiDung = nd;
        this.id_NoiDungCauHoi = id;
    }
     
    public  List<CauHoi> findCauHoi() throws ClassNotFoundException, SQLException{
        Statement stm = null;
     
        List<CauHoi> ds = new ArrayList<>();
            stm = connectJDBC().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM englist_test.cauhoi"
                    + "where idNoiDungCauHoi='"+this.id_NoiDungCauHoi+"'");
            while(rs.next())
            {
                Level lv = new Level();
                DanhMuc dm = new DanhMuc();
                CauHoi ch = new CauHoi();
                ch.setMaCauHoi(rs.getString("id_cauhoi"));
                ch.setNoiDung(rs.getString("noiDungcauhoi"));
//               ch.setLevel(rs.getString("level_cauhoi"));
//                ch.setDanhMuc(rs.getString("from_cauhoi"));
               lv.setLevel(rs.getString("level_cauhoi"));
               dm.setDanhMuc(rs.getString("from_cauhoi"));
               ch.setDanhMuc(dm);
               ch.setLevel(lv);
               ds.add(ch);
            }
            if(stm != null){
                stm.close();
            }
            connectJDBC().close();
            return ds;
    }
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(String.format("%s", this.noiDung));
         for(int i=0;i<this.dsCauHoi.size();i++)
        {
          b.append(String.format("%s", this.dsCauHoi.get(i)));
        }
        return b.toString();//To change body of generated methods, choose Tools | Templates.
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
     * @return the dsCauHoi
     */
    public List<CauHoi> getDsCauHoi() {
        return dsCauHoi;
    }

    /**
     * @param dsCauHoi the dsCauHoi to set
     */
    public void setDsCauHoi(List<CauHoi> dsCauHoi) {
        this.dsCauHoi = dsCauHoi;
    }

    /**
     * @return the id_NoiDungCauHoi
     */
    public String getId_NoiDungCauHoi() {
        return id_NoiDungCauHoi;
    }

    /**
     * @param id_NoiDungCauHoi the id_NoiDungCauHoi to set
     */
    public void setId_NoiDungCauHoi(String id_NoiDungCauHoi) {
        this.id_NoiDungCauHoi = id_NoiDungCauHoi;
    }

    /**
     * @return the danhMuc
     */
    public String getDanhMuc() {
        return danhMuc;
    }

    /**
     * @param danhMuc the danhMuc to set
     */
    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }
  
}
