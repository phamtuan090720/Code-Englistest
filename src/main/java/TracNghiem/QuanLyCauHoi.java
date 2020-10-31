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

/**
 *
 * @author MyPC
 */ 
public class QuanLyCauHoi {
    private List<CauHoi> dsCauHoi = new ArrayList<>();
    public static Connection connectJDBC() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/englist_test", "root", "123456789");
        return conn;
    }
    public void findAllCauHoi() throws SQLException, ClassNotFoundException{
        Statement stm = null;
            stm = connectJDBC().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM englist_test.cauhoi");
            while(rs.next())
            {
                Level lv = new Level();
                DanhMuc dm = new DanhMuc();
                CauHoi ch = new CauHoi();
                ch.setMaCauHoi(rs.getString("id_cauhoi"));
                ch.setNoiDung(rs.getString("noiDungcauhoi"));
//                ch.setLevel(rs.getString("level_cauhoi"));
//                ch.setDanhMuc(rs.getString("from_cauhoi"));
                lv.setLevel(rs.getString("level_cauhoi"));
                dm.setDanhMuc(rs.getString("from_cauhoi"));
                ch.setDanhMuc(dm);
                ch.setLevel(lv);
                this.dsCauHoi.add(ch);
            }
            if(stm!=null)
            {
                stm.close();
            }
            connectJDBC().close();
    }
     public List<CauHoi> findAllCauHoi1() throws SQLException, ClassNotFoundException{
            Statement stm = null;
            stm = connectJDBC().createStatement();
            List<CauHoi> ds = new ArrayList<>();
           
            ResultSet rs = stm.executeQuery("SELECT * FROM englist_test.cauhoi");
            while(rs.next())
            {
                Level lv = new Level();
                DanhMuc dm = new DanhMuc();
                CauHoi ch = new CauHoi();
                ch.setMaCauHoi(rs.getString("id_cauhoi"));
                ch.setNoiDung(rs.getString("noiDungcauhoi"));
                lv.setLevel(rs.getString("level_cauhoi"));
                dm.setDanhMuc(rs.getString("from_cauhoi"));
                ch.setDanhMuc(dm);
                ch.setLevel(lv);
                ds.add(ch);
            }
            if(stm!=null)
            {
                stm.close();
            }
            connectJDBC().close();
            return ds;
            
    }

    
    public void hienThiDs(){
         for(CauHoi h : this.dsCauHoi)
            {
                h.hienThi();
            }
    }
    /**
     * Tìm Kiếm Câu Hỏi
     * @param String x.
     * @return CauHoi ch.
     * @return null.
     */
    public CauHoi SearchQuestion(String x){
         for(CauHoi ch : this.dsCauHoi)
        {
            if(ch.getMaCauHoi().contains(x)== true)
                return ch;
        }
        return null;
    }
    /**
     * Tìm Kiếm Câu Hỏi Dựa Trên Danh Mục Câu Hỏi
     * @return List<CauHoi>
     */
    public List<CauHoi> SearchQuestionByCategory(String keyword)
    {
        List<CauHoi> kq = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for(CauHoi ch : this.dsCauHoi)
        {
            if(ch.getDanhMuc().toString().toLowerCase().contains(keyword))
            {
                kq.add(ch);
            }
        }
        return kq;
        
    }
    public List<CauHoi> SearchQuestionbyLevel(String keyword)
    {
        List<CauHoi> kq = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for(CauHoi ch : this.dsCauHoi)
        {
            if(ch.getLevel().toString().toLowerCase().contains(keyword))
            {
                kq.add(ch);
            }
        }
        return kq;
        
    }
    public List<CauHoi> SearchQuestionByContent(String keyword)
    {
        List<CauHoi> kq = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for(CauHoi ch : this.dsCauHoi)
        {
            if(ch.getNoiDung().toLowerCase().contains(keyword)== true)
            {
                kq.add(ch);
            }
        }
        return kq;
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
    
}
