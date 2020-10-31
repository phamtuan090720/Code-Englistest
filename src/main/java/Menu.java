/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MyPC
 */
public class Menu {

    public Menu() {
    }

    public static void menuDangKy() {
        System.out.println("==========Menu=========");
        System.out.println("1.Sign up");
        System.out.println("2.Log in");
        System.out.println("3.Exit");
        System.out.println("=======================");

    }

    public static void menuQuanLyHocVien() {
        System.out.println("==========Menu=========");
        System.out.println("1.Display the Student list");
        System.out.println("2.Seach Student");
        System.out.println("3.Update Imformation Student");
        System.out.println("4.Delete Student");
        System.out.println("5.Seach Question");
        System.out.println("6.Do exercise");
         System.out.println("7.Statistic ");
        System.out.println("8.Log out");
        System.out.println("=======================");
    }

    public static void menuSeachStudent() {
        System.out.println("==========Menu=========");
        System.out.println("1.Seach student by Name");
        System.out.println("2.Seach student by BirthDay");
        System.out.println("3.Seach student by Sex");
        System.out.println("4.Seach student by Address");
        System.out.println("5.Return");
        System.out.println("=======================");
    }

    public static void QuanLyCauHoi() {
        System.out.println("==========Menu=========");
        System.out.println("1.Seach Question by Content");
        System.out.println("2.Seach Question by Category");
        System.out.println("3.Seach Question by Level");
        System.out.println("4.Return");
        System.out.println("=======================");
    }

    public static void menulevel() {
        System.out.println("=====Level=====");
        System.out.println("1.Easy");
        System.out.println("2.Difficult");
        System.out.println("===============");

    }

    public static void menuCategory() {
        System.out.println("=====category=====");
        System.out.println("1.Multiple Choice");
        System.out.println("2.Incomplete");
        System.out.println("3.Conversation");
        System.out.println("==================");

    }

}
