/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.User;

public class UserDAO implements DAOInterface<User> {

    public static UserDAO getInstance() {
        return new UserDAO();
    }

    @Override
    public int insert(User t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO `user` (companyname, address, phone_number, poin) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getCompanyName());
            pst.setString(2, t.getAddress());
            pst.setString(3, t.getPhoneNumber());
            if (t.getPoin() == null)
                pst.setNull(4, java.sql.Types.INTEGER);
            else
                pst.setInt(4, t.getPoin());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(User t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE `user` SET companyname=?, address=?, phone_number=?, poin=? WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getCompanyName());
            pst.setString(2, t.getAddress());
            pst.setString(3, t.getPhoneNumber());
            if (t.getPoin() == null)
                pst.setNull(4, java.sql.Types.INTEGER);
            else
                pst.setInt(4, t.getPoin());
            pst.setInt(5, t.getId());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(User t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM `user` WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getId());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<User> selectAll() {
        ArrayList<User> list = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT id, companyname, address, phone_number, poin FROM `user`";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String companyName = rs.getString("companyname");
                String address = rs.getString("address");
                String phone = rs.getString("phone_number");
                Integer poin = (Integer) rs.getObject("poin");
                list.add(new User(id, companyName, address, phone, poin));
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User selectById(String t) {
        User u = null;
        try {
            int id = Integer.parseInt(t);
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT id, companyname, address, phone_number, poin FROM `user` WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                u = new User(
                        rs.getInt("id"),
                        rs.getString("companyname"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        (Integer) rs.getObject("poin"));
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public ArrayList<User> searchByCompanyName(String keyword) {
        ArrayList<User> list = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT id, companyname, address, phone_number, poin FROM `user` WHERE companyname LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("id"),
                        rs.getString("companyname"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        (Integer) rs.getObject("poin")));
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
