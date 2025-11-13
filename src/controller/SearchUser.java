/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.KhachHangDAO;
import java.util.ArrayList;
import model.KhachHang;

public class SearchUser {

    public static SearchUser getInstance() {
        return new SearchUser();
    }

    public ArrayList<KhachHang> searchTatCa(String text) {
        ArrayList<KhachHang> result = new ArrayList<>();
        ArrayList<KhachHang> users = KhachHangDAO.getInstance().selectAll();
        for (KhachHang u : users) {
            if ((u.getCompanyName() != null && u.getCompanyName().toLowerCase().contains(text.toLowerCase()))
                    || (u.getAddress() != null && u.getAddress().toLowerCase().contains(text.toLowerCase()))
                    || (u.getPhoneNumber() != null && u.getPhoneNumber().toLowerCase().contains(text.toLowerCase()))) {
                result.add(u);
            }
        }
        return result;
    }

    public ArrayList<KhachHang> searchByCompany(String text) {
        ArrayList<KhachHang> result = new ArrayList<>();
        ArrayList<KhachHang> users = KhachHangDAO.getInstance().selectAll();
        for (KhachHang u : users) {
            if (u.getCompanyName() != null && u.getCompanyName().toLowerCase().contains(text.toLowerCase())) {
                result.add(u);
            }
        }
        return result;
    }
}
