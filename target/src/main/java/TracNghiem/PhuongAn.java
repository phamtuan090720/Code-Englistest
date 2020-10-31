/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TracNghiem;

import java.util.Scanner;

/**
 *
 * @author MyPC
 */
public class PhuongAn {
    private String noiDung;
    private String giaiThich;
    private boolean dung = true;
    public PhuongAn(String nd, boolean dung,String gt)
    {
        this.giaiThich = gt;
        this.dung = dung;
        this.noiDung = nd;
    }
    public PhuongAn(){
    }

    @Override
    public String toString() {
        return String.format("%s %s\n",this.noiDung,this.dung); //To change body of generated methods, choose Tools | Templates.
    }
    public void nhapGiaiThich(Scanner scanner)
    {
        System.out.print("Nhap Chu Thich: ");
        this.setGiaiThich(scanner.nextLine());
    }
    public void nhapPhuongAn(Scanner scanner) {
        System.out.println("Nhap noi dung phuong an");
        this.noiDung = scanner.nextLine();
        System.out.print("Dung(d)/Sai(s): ");
        String da = scanner.nextLine();
        String d = "d";
        String s = "s";
        if (da.equals(d) == true) {
            this.dung = true;
        } else {
            this.dung = false;
        }
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
     * @return the dung
     */
    public boolean isDung() {
        return dung;
    }

    /**
     * @param dung the dung to set
     */
    public void setDung(boolean dung) {
        this.dung = dung;
    }

    /**
     * @return the giaiThich
     */
    public String getGiaiThich() {
        return giaiThich;
    }

    /**
     * @param giaiThich the giaiThich to set
     */
    public void setGiaiThich(String giaiThich) {
        this.giaiThich = giaiThich;
    }

    /**
     * @return the giaiThich
     */
   
}
