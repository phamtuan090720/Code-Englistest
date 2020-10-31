/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TracNghiem;

import static TracNghiem.CauHoi.connectJDBC;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author MyPC
 */
public class Level {
    private int id;
    private String level;
public Level() {
    }
public Level(String lv)
{
    this.level = lv;
}
public void themlevel(Scanner scanner) throws SQLException, ClassNotFoundException{
    PreparedStatement pstm = null;
    System.out.print("Nhap Level Ban Muon Them");
    String level = scanner.nextLine();
    String sql = "INSERT INTO englist_test.danhmuc (danhmuc) VALUES ('"+level+"')";
    pstm=connectJDBC().prepareCall(sql);
    pstm.executeLargeUpdate();
    if(pstm!=null)
    {
        pstm.close();
    }
}
   
public void nhap(Scanner scanner)
{
     this.level = scanner.nextLine();
}
public void themLevel()
{
}
    @Override
    public String toString() {
        return String.format("%s",this.level); //To change body of generated methods, choose Tools | Templates.
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
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }
}
