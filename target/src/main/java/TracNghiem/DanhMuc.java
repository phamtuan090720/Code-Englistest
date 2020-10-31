/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TracNghiem;

import static TracNghiem.CauHoi.connectJDBC;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author MyPC
 */
public class DanhMuc {
    private int id;
    private String danhMuc;
    public DanhMuc(){}
    public DanhMuc(String dm)
    {
        this.danhMuc = dm;
    }
    public void Nhap(Scanner scanner)
    {
         this.danhMuc = scanner.nextLine();
    }
public void themDanhMuc(Scanner scanner) throws SQLException, ClassNotFoundException{
    PreparedStatement pstm = null;
    System.out.print("Nhap Danh Muc Ban Muon Them");
    String danhMuc = scanner.nextLine();
    String sql = "INSERT INTO englist_test.danhmuc (danhmuc) VALUES ('"+danhMuc+"')";
    pstm=connectJDBC().prepareCall(sql);
    pstm.executeLargeUpdate();
    if(pstm!=null)
    {
        pstm.close();
    }
}
    @Override
    public String toString() {
        return String.format("%s", this.danhMuc); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
