package dao;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.KhachHang;

public class KhachHangDAO implements DAOInterface<KhachHang> {

    public static KhachHangDAO getInstance() {
        return new KhachHangDAO();
    }

    @Override
    public int insert(KhachHang t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO user (companyname, address, phone_number, poin) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getCompanyName());
            pst.setString(2, t.getAddress());
            pst.setString(3, t.getPhoneNumber());
            pst.setInt(4, t.getPoin());
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể thêm khách hàng: " + t.getCompanyName(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(KhachHang t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE user SET companyname=?, address=?, phone_number=?, poin=? WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getCompanyName());
            pst.setString(2, t.getAddress());
            pst.setString(3, t.getPhoneNumber());
            pst.setInt(4, t.getPoin());
            pst.setInt(5, t.getIdUser());
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể cập nhật khách hàng: " + t.getCompanyName(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(KhachHang t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM user WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getIdUser());
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể xóa khách hàng có ID: " + t.getIdUser(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<KhachHang> selectAll() {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String companyName = rs.getString("companyname");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");
                int poin = rs.getInt("poin");

                KhachHang kh = new KhachHang(id, companyName, address, phoneNumber, poin);
                dsKhachHang.add(kh);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

    @Override
    public KhachHang selectById(String idStr) {
        KhachHang kh = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(idStr));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String companyName = rs.getString("companyname");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");
                int poin = rs.getInt("poin");

                kh = new KhachHang(id, companyName, address, phoneNumber, poin);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }

    public KhachHang selectByCompanyName(String companyName) {
        KhachHang kh = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM user WHERE companyname = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, companyName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");
                int poin = rs.getInt("poin");
                kh = new KhachHang(id, companyName, address, phoneNumber, poin);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }

    public int updatePoin(int id, int poin) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE user SET poin = ? WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, poin);
            pst.setInt(2, id);
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}
