/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import java.util.ArrayList;
import model.User;

public class SearchUser {

    public static SearchUser getInstance() {
        return new SearchUser();
    }

    public ArrayList<User> searchTatCa(String text) {
        ArrayList<User> result = new ArrayList<>();
        ArrayList<User> users = UserDAO.getInstance().selectAll();
        for (User u : users) {
            if ((u.getCompanyName() != null && u.getCompanyName().toLowerCase().contains(text.toLowerCase()))
                    || (u.getAddress() != null && u.getAddress().toLowerCase().contains(text.toLowerCase()))
                    || (u.getPhoneNumber() != null && u.getPhoneNumber().toLowerCase().contains(text.toLowerCase()))) {
                result.add(u);
            }
        }
        return result;
    }

    public ArrayList<User> searchByCompany(String text) {
        return UserDAO.getInstance().searchByCompanyName(text);
    }
}
