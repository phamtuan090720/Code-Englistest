/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TracNghiem;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author MyPC
 */
public class Ramdom {
    public Ramdom(){}
    /**
     * dùm để ramdom cá phần tử trong danh sách,giá trị không trùng nhau.
     * @param int number.
     * @param  int a.
     * @return ArrayList tmp.
     */
    public static ArrayList ramdom(int number,int a) throws SQLException, ClassNotFoundException {

//Khai bao thanh phan trung gian
        ArrayList tmp = new ArrayList();
        int count = 0;
        int value;
        boolean flag;

        while (count < number) {
            flag = false;
            value = (int) (Math.random() * a);
            for (int i = 0; i < tmp.size(); i++) {

                if (((Integer) tmp.get(i)) == value) {

                    flag = true;
                    break;

                }

            }

            if (!flag) {

                tmp.add(value);
                count++;

            }

        }

        return tmp;

    }
}