/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TracNghiem;

import static TracNghiem.QuanLyCauHoi.connectJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MyPC
 */
public class QuanLyNoiDungCauHoi {
    private List<NoiDungCauHoi> ds = new ArrayList<>();
    /**
     * Tìm Câu Hòi 
     * @param from
     * @return List<NoiDungCauHoi>
     */
    public List<NoiDungCauHoi> findNdCauHoi(String from) throws ClassNotFoundException, SQLException{
        Statement stm = null;
        
        List<NoiDungCauHoi> ds = new ArrayList<>();
        stm = connectJDBC().createStatement();
//        String sql = "SELECT DISTINCT  nd.*FROM noidungcauhoi nd , cauhoi ch "
//                + "where ch.idNoiDungCauHoi=nd.idNoiDungCauHoi "
//                + "and from_cauhoi ='""'";
        String sql = "SELECT * FROM englist_test.noidungcauhoi where danhMuc='"+from+"'";
        ResultSet rs = stm.executeQuery(sql);
        while(rs.next())
        {   
            NoiDungCauHoi nd = new NoiDungCauHoi();
            nd.setId_NoiDungCauHoi(rs.getString("idNoiDungCauHoi"));
            nd.setNoiDung(rs.getString("noiDung"));
            ds.add(nd);
        }
        return ds;
    }

    /**
     * @return the ds
     */
    public List<NoiDungCauHoi> getDs() {
        return ds;
    }

    /**
     * @param ds the ds to set
     */
    public void setDs(List<NoiDungCauHoi> ds) {
        this.ds = ds;
    }
    
}
