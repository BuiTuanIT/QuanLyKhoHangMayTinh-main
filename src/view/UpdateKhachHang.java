/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatLightLaf;
import dao.KhachHangDAO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.KhachHang;

/**
 *
 * @author You
 */
public class UpdateKhachHang extends javax.swing.JDialog {

    private KhachHangForm parent;

    public UpdateKhachHang(javax.swing.JInternalFrame parent, javax.swing.JFrame owner, boolean modal) {
        super(owner, modal);
        this.parent = (KhachHangForm) parent;
        initComponents();
        setLocationRelativeTo(null);
        displayInfo();
    }

    private void displayInfo() {
        KhachHang kh = parent.getKhachHangSelect();
        txtID.setText(String.valueOf(kh.getIdUser()));
        txtTen.setText(kh.getCompanyName());
        txtDiaChi.setText(kh.getAddress());
        txtSdt.setText(kh.getPhoneNumber());
        txtDiem.setText(String.valueOf(kh.getPoin()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDiem = new javax.swing.JTextField();
        btnLuu = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cập nhật khách hàng");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("ID Khách hàng");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 120, -1));

        txtID.setEnabled(false);
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 298, 38));

        jLabel3.setText("Tên công ty / khách hàng");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, 24));
        jPanel1.add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 298, 38));

        jLabel4.setText("Số điện thoại");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 100, -1));
        jPanel1.add(txtSdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 298, 38));

        jLabel5.setText("Địa chỉ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 50, -1));
        jPanel1.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 298, 38));

        jLabel6.setText("Điểm tích lũy");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 100, -1));
        jPanel1.add(txtDiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 298, 38));

        btnLuu.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        btnLuu.setForeground(new java.awt.Color(255, 255, 255));
        btnLuu.setText("Lưu");
        btnLuu.setBorder(null);
        btnLuu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLuu.addActionListener(evt -> btnLuuActionPerformed(evt));
        jPanel1.add(btnLuu, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 140, 38));

        btnHuy.setBackground(new java.awt.Color(255, 0, 51));
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("Huỷ");
        btnHuy.setBorder(null);
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuy.addActionListener(evt -> dispose());
        jPanel1.add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 140, 38));

        jPanel2.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));

        jLabel1.setFont(new java.awt.Font("SF Pro Display", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CẬP NHẬT KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel1)
                                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(22, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(19, 19, 19))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>                        

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String ten = txtTen.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String sdt = txtSdt.getText().trim();
            String diemStr = txtDiem.getText().trim();

            // ======== KIỂM TRA RỖNG ========
            if (ten.isEmpty() || diaChi.isEmpty() || sdt.isEmpty() || diemStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ thông tin trước khi lưu!",
                        "Thiếu thông tin",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // ======== KIỂM TRA SỐ ĐIỆN THOẠI ========
            if (!sdt.matches("\\d{9,11}")) {  // chỉ cho phép 9–11 chữ số
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không hợp lệ! Vui lòng nhập 9–11 chữ số.",
                        "Lỗi định dạng",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ======== KIỂM TRA ĐIỂM ========
            int diem;
            try {
                diem = Integer.parseInt(diemStr);
                if (diem < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Điểm tích lũy phải là số nguyên không âm!",
                            "Lỗi giá trị",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Điểm tích lũy phải là số nguyên hợp lệ!",
                        "Lỗi định dạng",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ======== CẬP NHẬT KHÁCH HÀNG ========
            KhachHang kh = parent.getKhachHangSelect();
            kh.setCompanyName(ten);
            kh.setAddress(diaChi);
            kh.setPhoneNumber(sdt);
            kh.setPoin(diem);

            int result = KhachHangDAO.getInstance().update(kh);
            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật khách hàng thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                parent.loadDataToTable(KhachHangDAO.getInstance().selectAll());
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không thể cập nhật khách hàng. Vui lòng thử lại!",
                        "Thất bại",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi trong quá trình cập nhật!",
                    "Lỗi hệ thống",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatLightLaf());
        java.awt.EventQueue.invokeLater(() -> {
            UpdateKhachHang dialog = new UpdateKhachHang(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLuu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDiem;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTen;
    // End of variables declaration                   
}
