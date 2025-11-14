/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.util.Comparator;
import controller.ConvertDate;
import controller.SearchAccount;
import dao.AccountDAO;
import dao.MayTinhDAO;
import dao.NhaCungCapDAO;
import dao.PhieuNhapDAO;
import dao.PhieuXuatDAO;
import dao.ThongKeDAO;
import dao.KhachHangDAO;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.PhieuNhap;
import java.util.Iterator;
import model.Account;
import model.Phieu;
import model.PhieuXuat;
import model.ThongKeProduct;
import model.KhachHang;

/**
 *
 * @author Robot
 */
public class ThongKeForm extends javax.swing.JInternalFrame {

    private DefaultTableModel tblModel;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");

    public DecimalFormat getFormatter() {
        return formatter;
    }

    public SimpleDateFormat getFormatDate() {
        return formatDate;
    }

    public ThongKeForm() {
        initComponents();
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        jDateChooserFrom.setDateFormatString("dd/MM/yyyy");
        jDateChooserTo.setDateFormatString("dd/MM/yyyy");
        jDateChooserFromPr.setDateFormatString("dd/MM/yyyy");
        jDateChooserToPr.setDateFormatString("dd/MM/yyyy");
        txtQuantityProduct.setText(Integer.toString(MayTinhDAO.getInstance().getSl()));
        txtQuantityNcc.setText(Integer.toString(NhaCungCapDAO.getInstance().selectAll().size()));
        txtQuantityUser.setText(Integer.toString(AccountDAO.getInstance().selectAll().size()));
        //
        initTable();
        loadDataToTable();
        changeTextFind();
        //
        loadDataToTableAcc(AccountDAO.getInstance().selectAll());
        tblAccount.setDefaultEditor(Object.class, null);
        tblThongKeProduct.setDefaultEditor(Object.class, null);
        tblPhieuNhap.setDefaultEditor(Object.class, null);
        //
        loadDataToTableThongKeProduct(ThongKeDAO.getInstance().getThongKe());

        // Khởi tạo biểu đồ tròn ở tab "Biểu đồ"
        initPieChartTab();

        // Khởi tạo Top khách hàng
        initTopCustomersTab();

        // Khởi tạo Top nhân viên
        initTopStaffTab();
    }

    public final void initTable() {
        tblModel = new DefaultTableModel();
        String[] headerTbl = new String[] { "Mã phiếu nhập", "Người tạo", "Thời gian tạo", "Tổng tiền" };
        tblModel.setColumnIdentifiers(headerTbl);
        tblPhieuNhap.setModel(tblModel);
        tblPhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    private void loadDataToTable() {
        try {
            ArrayList<PhieuNhap> allPhieu = PhieuNhapDAO.getInstance().selectAll();
            tblModel.setRowCount(0);
            for (int i = 0; i < allPhieu.size(); i++) {
                tblModel.addRow(new Object[] {
                        allPhieu.get(i).getMaPhieu(), allPhieu.get(i).getNguoiTao(),
                        formatDate.format(allPhieu.get(i).getThoiGianTao()),
                        formatter.format(allPhieu.get(i).getTongTien()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    private void loadDataToTableSearch(ArrayList<Phieu> result) {
        try {
            tblModel.setRowCount(0);
            for (int i = 0; i < result.size(); i++) {
                tblModel.addRow(new Object[] {
                        result.get(i).getMaPhieu(), result.get(i).getNguoiTao(),
                        formatDate.format(result.get(i).getThoiGianTao()),
                        formatter.format(result.get(i).getTongTien()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    public ArrayList<Phieu> searchTatCa(String text) {
        ArrayList<Phieu> result = new ArrayList<>();
        ArrayList<Phieu> armt = PhieuNhapDAO.getInstance().selectAllP();
        for (var phieu : armt) {
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    public ArrayList<Phieu> searchPhieuNhap(String text) {
        ArrayList<Phieu> result = new ArrayList<>();
        ArrayList<PhieuNhap> armt = PhieuNhapDAO.getInstance().selectAll();
        for (var phieu : armt) {
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    public ArrayList<Phieu> searchPhieuXuat(String text) {
        ArrayList<Phieu> result = new ArrayList<>();
        ArrayList<PhieuXuat> armt = PhieuXuatDAO.getInstance().selectAll();
        for (var phieu : armt) {
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    public void changeTextFind() {
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                /* do nothing */
                if (jTextFieldSearch.getText().length() == 0) {
                    loadDataToTable();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        txtNamePr = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblThongKeProduct = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jDateChooserFromPr = new com.toedter.calendar.JDateChooser();
        jDateChooserToPr = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnResetThongKePr = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnDetail = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxLuaChon = new javax.swing.JComboBox<>();
        jTextFieldSearch = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jDateChooserFrom = new com.toedter.calendar.JDateChooser();
        jDateChooserTo = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        giaTu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        giaDen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        soLuong = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tongTien = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jComboBoxLuaChon1 = new javax.swing.JComboBox<>();
        jTextFieldSearch1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAccount = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtQuantityProduct = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtQuantityNcc = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtQuantityUser = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setPreferredSize(new java.awt.Dimension(1180, 770));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1180, 770));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNamePr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamePrKeyReleased(evt);
            }
        });
        jPanel16.add(txtNamePr, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 350, 40));

        tblThongKeProduct.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null }
                },
                new String[] {
                        "STT", "Mã máy", "Tên máy", "Số lượng nhập", "Số lượng xuất"
                }));
        tblThongKeProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongKeProductMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblThongKeProduct);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo ngày"));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDateChooserFromPr.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserFromPr.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFromPrPropertyChange(evt);
            }
        });
        jDateChooserFromPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserFromPrKeyReleased(evt);
            }
        });
        jPanel13.add(jDateChooserFromPr, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 170, -1));

        jDateChooserToPr.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserToPr.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserToPrPropertyChange(evt);
            }
        });
        jDateChooserToPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserToPrKeyReleased(evt);
            }
        });
        jPanel13.add(jDateChooserToPr, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 170, -1));

        jLabel6.setText("Đến");
        jPanel13.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 40, 20));

        jLabel8.setText("Từ");
        jPanel13.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 20, 20));

        btnResetThongKePr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnResetThongKePr.setText("Làm mới");
        btnResetThongKePr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetThongKePrActionPerformed(evt);
            }
        });
        jPanel13.add(btnResetThongKePr, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 140, 40));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1174,
                                                Short.MAX_VALUE)
                                        .addGroup(jPanel15Layout.createSequentialGroup()
                                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 397,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap()));
        jPanel15Layout.setVerticalGroup(
                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 84,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 86,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 463,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(53, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));
        jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(510, 510, 510)));

        jTabbedPane1.addTab("Sản phẩm", jPanel12);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        jToolBar1.setRollover(true);

        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_eye_40px.png"))); // NOI18N
        btnDetail.setText("Xem chi tiết");
        btnDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetail.setFocusable(false);
        btnDetail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDetail);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxLuaChon.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Phiếu Nhập", "Phiếu Xuất" }));
        jComboBoxLuaChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLuaChonActionPerformed(evt);
            }
        });
        jComboBoxLuaChon.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBoxLuaChonPropertyChange(evt);
            }
        });
        jPanel3.add(jComboBoxLuaChon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 40));

        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });
        jPanel3.add(jTextFieldSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 310, 40));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        jButton7.setText("Làm mới");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 140, 40));

        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {

                }));
        jScrollPane1.setViewportView(tblPhieuNhap);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo ngày"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDateChooserFrom.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFromPropertyChange(evt);
            }
        });
        jDateChooserFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserFromKeyReleased(evt);
            }
        });
        jPanel4.add(jDateChooserFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 170, -1));

        jDateChooserTo.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserToPropertyChange(evt);
            }
        });
        jDateChooserTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserToKeyReleased(evt);
            }
        });
        jPanel4.add(jDateChooserTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 170, -1));

        jLabel1.setText("Đến");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 40, 20));

        jLabel5.setText("Từ");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 20, 20));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo giá"));

        jLabel3.setText("Từ");

        giaTu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaTuActionPerformed(evt);
            }
        });
        giaTu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaTuKeyReleased(evt);
            }
        });

        jLabel4.setText("Đến");

        giaDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaDenActionPerformed(evt);
            }
        });
        giaDen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaDenKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel4)
                                .addGap(28, 28, 28)
                                .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE, 180,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(84, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(22, Short.MAX_VALUE)));

        jLabel2.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel2.setText("TỔNG TIỀN");

        soLuong.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        soLuong.setText("0");

        jLabel7.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel7.setText("TỔNG PHIẾU ");

        tongTien.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        tongTien.setForeground(new java.awt.Color(255, 0, 51));
        tongTien.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        1167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jToolBar1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 386,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jPanel3,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 776,
                                                                        Short.MAX_VALUE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jPanel4,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jPanel2,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(37, 37, 37)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 138,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(soLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 305,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(13, 13, 13)))
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 84,
                                                Short.MAX_VALUE)
                                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(soLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(203, 203, 203)));

        jTabbedPane1.addTab("Phiếu", jPanel1);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxLuaChon1.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "FullName", "UserName", "Role" }));
        jComboBoxLuaChon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLuaChon1ActionPerformed(evt);
            }
        });
        jComboBoxLuaChon1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBoxLuaChon1PropertyChange(evt);
            }
        });
        jPanel7.add(jComboBoxLuaChon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 40));

        jTextFieldSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearch1KeyReleased(evt);
            }
        });
        jPanel7.add(jTextFieldSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 320, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        jButton1.setText("Làm mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 160, 40));

        tblAccount.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null }
                },
                new String[] {
                        "Họ và tên", "Email", "Tên người dùng", "Vai trò", "Tình trạng"
                }));
        tblAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAccountMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAccount);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1163,
                                                Short.MAX_VALUE))
                                .addContainerGap(11, Short.MAX_VALUE)));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 453,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(149, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(510, 510, 510)));

        jTabbedPane1.addTab("Tài khoản", jPanel5);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1192, Short.MAX_VALUE));
        jPanel17Layout.setVerticalGroup(
                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1228, Short.MAX_VALUE));

        jTabbedPane1.addTab("Biểu đồ", jPanel17);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1192, Short.MAX_VALUE));
        jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1228, Short.MAX_VALUE));

        jTabbedPane1.addTab("Top khách hàng", jPanel14);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, 620));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 204, 0));

        txtQuantityProduct.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtQuantityProduct.setForeground(new java.awt.Color(255, 255, 255));
        txtQuantityProduct.setText("100");

        jLabel10.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sản phẩm trong kho");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-monitor-80.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtQuantityProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 108,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10))
                                .addGap(73, 73, 73)));
        jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addContainerGap(12, Short.MAX_VALUE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12)
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(txtQuantityProduct,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 38,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel10)))
                                .addGap(10, 10, 10)));

        jPanel10.setBackground(new java.awt.Color(255, 102, 0));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-supplier-80.png"))); // NOI18N

        txtQuantityNcc.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtQuantityNcc.setForeground(new java.awt.Color(255, 255, 255));
        txtQuantityNcc.setText("100");

        jLabel14.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nhà cung cấp ");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel13)
                                .addGap(43, 43, 43)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtQuantityNcc, javax.swing.GroupLayout.PREFERRED_SIZE, 108,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 163,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(60, Short.MAX_VALUE)));
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13)
                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(txtQuantityNcc, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel14)))
                                .addGap(10, 10, 10)));

        jPanel11.setBackground(new java.awt.Color(0, 204, 204));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-account-80.png"))); // NOI18N

        txtQuantityUser.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtQuantityUser.setForeground(new java.awt.Color(255, 255, 255));
        txtQuantityUser.setText("100");

        jLabel17.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Tài khoản người dùng");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtQuantityUser, javax.swing.GroupLayout.PREFERRED_SIZE, 108,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17))
                                .addGap(73, 73, 73)));
        jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15)
                                        .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(txtQuantityUser, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel17)))
                                .addGap(10, 10, 10)));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28,
                                        Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)));
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel8Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 130));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        loadDataToTable();
        jComboBoxLuaChon.setSelectedIndex(0);
        jTextFieldSearch.setText("");
        jDateChooserFrom.setCalendar(null);
        jDateChooserTo.setCalendar(null);
        giaDen.setText("");
        giaTu.setText("");
    }// GEN-LAST:event_jButton7ActionPerformed

    private void giaDenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_giaDenActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_giaDenActionPerformed

    private void jComboBoxLuaChonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxLuaChonActionPerformed
        try {
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }// GEN-LAST:event_jComboBoxLuaChonActionPerformed

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldSearchKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }// GEN-LAST:event_jTextFieldSearchKeyReleased

    private void giaTuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_giaTuActionPerformed
        // TODO add your handling code here:

    }// GEN-LAST:event_giaTuActionPerformed

    private void giaTuKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_giaTuKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_giaTuKeyReleased

    private void jDateChooserFromKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jDateChooserFromKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jDateChooserFromKeyReleased

    private void jDateChooserToKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jDateChooserToKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jDateChooserToKeyReleased

    private void giaDenKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_giaDenKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_giaDenKeyReleased

    private void jDateChooserFromPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jDateChooserFromPropertyChange
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jDateChooserFromPropertyChange

    private void jDateChooserToPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jDateChooserToPropertyChange
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jDateChooserToPropertyChange

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDetailActionPerformed
        // TODO add your handling code here:
        if (tblPhieuNhap.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu !");
        } else {
            CTThongKe a = new CTThongKe(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this),
                    rootPaneCheckingEnabled);
            a.setVisible(true);
        }
    }// GEN-LAST:event_btnDetailActionPerformed

    private void jComboBoxLuaChonPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jComboBoxLuaChonPropertyChange

    }// GEN-LAST:event_jComboBoxLuaChonPropertyChange

    private void jComboBoxLuaChon1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxLuaChon1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBoxLuaChon1ActionPerformed

    private void jComboBoxLuaChon1PropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jComboBoxLuaChon1PropertyChange
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBoxLuaChon1PropertyChange

    private void jTextFieldSearch1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldSearch1KeyReleased
        // TODO add your handling code here:
        String luachon = (String) jComboBoxLuaChon1.getSelectedItem();
        String searchContent = jTextFieldSearch1.getText();
        ArrayList<Account> result = new ArrayList<>();
        switch (luachon) {
            case "Tất cả":
                result = SearchAccount.getInstance().searchTatCaAcc(searchContent);
                break;
            case "Tên tài khoản":
                result = SearchAccount.getInstance().searchFullName(searchContent);
                break;
            case "Tên đăng nhập":
                result = SearchAccount.getInstance().searchUserName(searchContent);
                break;
            case "Vai trò":
                result = SearchAccount.getInstance().searchRole(searchContent);
                break;
        }
        loadDataToTableAcc(result);
    }// GEN-LAST:event_jTextFieldSearch1KeyReleased

    private void tblAccountMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblAccountMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultTableModel tblAcc = (DefaultTableModel) tblAccount.getModel();
            if (tblAccount.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản");
            } else {
                CTThongKeAcc tk;
                tk = new CTThongKeAcc(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this),
                        rootPaneCheckingEnabled);
                tk.setVisible(true);
            }
        }
    }// GEN-LAST:event_tblAccountMouseClicked

    private void txtNamePrKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtNamePrKeyReleased
        try {
            // TODO add your handling code here:
            filterThongKeSanPham();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_txtNamePrKeyReleased

    private void tblThongKeProductMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblThongKeProductMouseClicked
        // TODO add your handling code here:
    }// GEN-LAST:event_tblThongKeProductMouseClicked

    private void jDateChooserFromPrPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jDateChooserFromPrPropertyChange
        try {
            // TODO add your handling code here:
            filterThongKeSanPham();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jDateChooserFromPrPropertyChange

    private void jDateChooserFromPrKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jDateChooserFromPrKeyReleased
        // TODO add your handling code here:
    }// GEN-LAST:event_jDateChooserFromPrKeyReleased

    private void jDateChooserToPrPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_jDateChooserToPrPropertyChange
        try {
            // TODO add your handling code here:
            filterThongKeSanPham();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jDateChooserToPrPropertyChange

    private void jDateChooserToPrKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jDateChooserToPrKeyReleased
        // TODO add your handling code here:
    }// GEN-LAST:event_jDateChooserToPrKeyReleased

    private void btnResetThongKePrActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnResetThongKePrActionPerformed
        // TODO add your handling code here:
        txtNamePr.setText("");
        jDateChooserFromPr.setCalendar(null);
        jDateChooserToPr.setCalendar(null);
        loadDataToTableThongKeProduct(ThongKeDAO.getInstance().getThongKe());
    }// GEN-LAST:event_btnResetThongKePrActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jComboBoxLuaChon1.setSelectedIndex(0);
        jTextFieldSearch1.setText("");
        loadDataToTableAcc(AccountDAO.getInstance().selectAll());
    }// GEN-LAST:event_jButton1ActionPerformed

    private void initPieChartTab() {
        try {
            jPanel17.removeAll();
            jPanel17.setLayout(new BorderLayout());

            javax.swing.JPanel control = new javax.swing.JPanel();
            control.setBackground(java.awt.Color.WHITE);
            control.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
            javax.swing.JLabel lbMonth = new javax.swing.JLabel("Tháng:");
            javax.swing.JComboBox<String> cboMonth = new javax.swing.JComboBox<>();
            cboMonth.addItem("Tất cả");
            for (int m = 1; m <= 12; m++) {
                cboMonth.addItem(String.valueOf(m));
            }
            javax.swing.JLabel lbYear = new javax.swing.JLabel("Năm:");
            javax.swing.JComboBox<String> cboYear = new javax.swing.JComboBox<>();
            cboYear.addItem("Tất cả");
            java.util.Calendar cal = java.util.Calendar.getInstance();
            int currentYear = cal.get(java.util.Calendar.YEAR);
            for (int y = currentYear; y >= currentYear - 10; y--) {
                cboYear.addItem(String.valueOf(y));
            }

            // Phóng to phần chọn tháng/năm
            java.awt.Font controlFont = control.getFont().deriveFont(Font.PLAIN, 14f);
            lbMonth.setFont(controlFont);
            lbYear.setFont(controlFont);
            cboMonth.setFont(controlFont);
            cboYear.setFont(controlFont);
            cboMonth.setPreferredSize(new Dimension(120, 34));
            cboYear.setPreferredSize(new Dimension(140, 34));

            control.add(lbMonth);
            control.add(cboMonth);
            control.add(lbYear);
            control.add(cboYear);

            PieChartPanel chart = new PieChartPanel();
            // Panel tổng tiền ở bên phải
            javax.swing.JPanel right = new javax.swing.JPanel();
            right.setBackground(java.awt.Color.WHITE);
            right.setBorder(javax.swing.BorderFactory.createTitledBorder("Tổng tiền"));
            right.setLayout(new java.awt.GridBagLayout());
            right.setPreferredSize(new Dimension(240, 10));
            java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
            gbc.gridx = 0;
            gbc.anchor = java.awt.GridBagConstraints.WEST;
            gbc.insets = new java.awt.Insets(4, 8, 4, 8);

            javax.swing.JLabel lblImportTitle = new javax.swing.JLabel("Nhập:");
            javax.swing.JLabel lblImportMoney = new javax.swing.JLabel("0đ");
            lblImportMoney.setForeground(new java.awt.Color(76, 175, 80));
            lblImportMoney.setFont(lblImportMoney.getFont().deriveFont(java.awt.Font.BOLD, 18f));

            javax.swing.JLabel lblExportTitle = new javax.swing.JLabel("Xuất:");
            javax.swing.JLabel lblExportMoney = new javax.swing.JLabel("0đ");
            lblExportMoney.setForeground(new java.awt.Color(244, 67, 54));
            lblExportMoney.setFont(lblExportMoney.getFont().deriveFont(java.awt.Font.BOLD, 18f));

            lblImportTitle.setFont(controlFont);
            lblExportTitle.setFont(controlFont);

            gbc.gridy = 0;
            right.add(lblImportTitle, gbc);
            gbc.gridy = 1;
            right.add(lblImportMoney, gbc);
            gbc.gridy = 2;
            right.add(lblExportTitle, gbc);
            gbc.gridy = 3;
            right.add(lblExportMoney, gbc);

            // Lắng nghe thay đổi để vẽ lại
            java.awt.event.ActionListener onChange = e -> {
                chart.setMonthYearSelection((String) cboMonth.getSelectedItem(), (String) cboYear.getSelectedItem());
                chart.repaint();

                java.util.Date[] range = getSelectedMonthRange((String) cboMonth.getSelectedItem(),
                        (String) cboYear.getSelectedItem());
                double importSum = getImportSum(range[0], range[1]);
                double exportSum = getExportSum(range[0], range[1]);
                lblImportMoney.setText(formatter.format(importSum) + "đ");
                lblExportMoney.setText(formatter.format(exportSum) + "đ");
            };
            cboMonth.addActionListener(onChange);
            cboYear.addActionListener(onChange);
            // Khởi tạo lần đầu
            chart.setMonthYearSelection((String) cboMonth.getSelectedItem(), (String) cboYear.getSelectedItem());
            {
                java.util.Date[] range = getSelectedMonthRange((String) cboMonth.getSelectedItem(),
                        (String) cboYear.getSelectedItem());
                double importSum = getImportSum(range[0], range[1]);
                double exportSum = getExportSum(range[0], range[1]);
                lblImportMoney.setText(formatter.format(importSum) + "đ");
                lblExportMoney.setText(formatter.format(exportSum) + "đ");
            }

            jPanel17.add(control, BorderLayout.NORTH);
            jPanel17.add(chart, BorderLayout.CENTER);
            jPanel17.add(right, BorderLayout.EAST);
            jPanel17.revalidate();
            jPanel17.repaint();
        } catch (Exception e) {
        }
    }

    private void initTopCustomersTab() {
        try {
            jPanel14.removeAll();
            jPanel14.setLayout(new BorderLayout());

            tblTopUsers = new javax.swing.JTable();
            tblTopUsers.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][] {},
                    new String[] { "Top", "Tên công ty", "Số điện thoại", "Địa chỉ", "Poin" }) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            jScrollPaneTopUsers = new javax.swing.JScrollPane(tblTopUsers);
            jPanel14.add(jScrollPaneTopUsers, BorderLayout.CENTER);

            loadTopCustomers();
            jPanel14.revalidate();
            jPanel14.repaint();
        } catch (Exception e) {
        }
    }

    private void loadTopCustomers() {
        try {
            ArrayList<KhachHang> users = KhachHangDAO.getInstance().selectAll();
            users.sort(new Comparator<KhachHang>() {
                @Override
                public int compare(KhachHang o1, KhachHang o2) {
                    return Integer.compare(o2.getPoin(), o1.getPoin());
                }
            });

            DefaultTableModel model = (DefaultTableModel) tblTopUsers.getModel();
            model.setRowCount(0);
            for (int i = 0; i < users.size(); i++) {
                KhachHang u = users.get(i);
                model.addRow(new Object[] {
                        (i + 1),
                        u.getCompanyName(),
                        u.getPhoneNumber(),
                        u.getAddress(),
                        u.getPoin()
                });
            }
        } catch (Exception e) {
        }
    }

    private void initTopStaffTab() {
        try {
            jPanelTopStaff = new javax.swing.JPanel();
            jPanelTopStaff.setLayout(new BorderLayout());
            tblTopStaff = new javax.swing.JTable();
            tblTopStaff.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][] {},
                    new String[] { "Top", "Họ và tên", "Tên đăng nhập", "Email", "Vai trò", "Poin" }) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            jScrollPaneTopStaff = new javax.swing.JScrollPane(tblTopStaff);
            jPanelTopStaff.add(jScrollPaneTopStaff, BorderLayout.CENTER);

            loadTopStaff();
            jTabbedPane1.addTab("Top nhân viên", jPanelTopStaff);
        } catch (Exception e) {
        }
    }

    private void loadTopStaff() {
        try {
            ArrayList<Account> accounts = AccountDAO.getInstance().selectAll();
            accounts.sort(new Comparator<Account>() {
                @Override
                public int compare(Account o1, Account o2) {
                    return Integer.compare(o2.getPoin(), o1.getPoin());
                }
            });
            DefaultTableModel model = (DefaultTableModel) tblTopStaff.getModel();
            model.setRowCount(0);
            for (int i = 0; i < accounts.size(); i++) {
                Account a = accounts.get(i);
                model.addRow(new Object[] {
                        (i + 1),
                        a.getFullName(),
                        a.getUser(),
                        a.getEmail(),
                        a.getRole(),
                        a.getPoin()
                });
            }
        } catch (Exception e) {
        }
    }

    public Phieu getPhieuNhapSelect() {
        int i_row = tblPhieuNhap.getSelectedRow();
        Phieu pn = PhieuNhapDAO.getInstance().selectById(tblModel.getValueAt(i_row, 1).toString());
        return pn;
    }

    public Phieu findPhieu() {
        int i_row = tblPhieuNhap.getSelectedRow();
        String text = tblModel.getValueAt(i_row, 0).toString();
        ArrayList<Phieu> phieuAll = PhieuNhapDAO.getInstance().selectAllP();
        Phieu phieuk = null;
        for (Phieu phieu : phieuAll) {
            if (phieu.getMaPhieu().equals(text)) {
                return phieu;
            }
        }
        return phieuk;
    }

    public boolean checkDate(Date dateTest, Date star, Date end) {
        return dateTest.getTime() >= star.getTime() && dateTest.getTime() <= end.getTime();
    }

    public ArrayList<PhieuNhap> searchDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        ArrayList<PhieuNhap> result = new ArrayList<PhieuNhap>();
        Date from = jDateChooserFrom.getDate();
        Date to = jDateChooserTo.getDate();
        ArrayList<PhieuNhap> armt = PhieuNhapDAO.getInstance().selectAll();
        for (var phieu : armt) {
            if (checkDate(phieu.getThoiGianTao(), from, to)) {
                result.add(phieu);
            }

        }
        return result;
    }

    public void loadDataToTableAcc(ArrayList<Account> acc) {
        try {
            DefaultTableModel tblModelAcc = (DefaultTableModel) tblAccount.getModel();
            tblModelAcc.setRowCount(0);
            for (Account i : acc) {
                tblModelAcc.addRow(new Object[] {
                        i.getFullName(), i.getEmail(), i.getUser(), i.getRole(),
                        i.getStatus() == 1 ? "Hoạt động" : "Đã khóa"
                });
            }
        } catch (Exception e) {
        }
    }

    public String getMaAcc() {
        return tblAccount.getValueAt(tblAccount.getSelectedRow(), 2).toString();
    }

    private int getImportOrderCount() {
        try {
            return PhieuNhapDAO.getInstance().selectAll().size();
        } catch (Exception e) {
            return 0;
        }
    }

    private int getExportOrderCount() {
        try {
            return PhieuXuatDAO.getInstance().selectAll().size();
        } catch (Exception e) {
            return 0;
        }
    }

    private int getImportOrderCount(java.util.Date from, java.util.Date to) {
        try {
            java.util.ArrayList<model.PhieuNhap> list = PhieuNhapDAO.getInstance().selectAll();
            int count = 0;
            for (model.PhieuNhap p : list) {
                if (from == null || to == null) {
                    count++;
                } else if (checkDate(p.getThoiGianTao(), from, to)) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    private int getExportOrderCount(java.util.Date from, java.util.Date to) {
        try {
            java.util.ArrayList<model.PhieuXuat> list = PhieuXuatDAO.getInstance().selectAll();
            int count = 0;
            for (model.PhieuXuat p : list) {
                if (from == null || to == null) {
                    count++;
                } else if (checkDate(p.getThoiGianTao(), from, to)) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    private double getImportSum(java.util.Date from, java.util.Date to) {
        try {
            java.util.ArrayList<model.PhieuNhap> list = PhieuNhapDAO.getInstance().selectAll();
            double sum = 0d;
            for (model.PhieuNhap p : list) {
                if (from == null || to == null || checkDate(p.getThoiGianTao(), from, to)) {
                    sum += p.getTongTien();
                }
            }
            return sum;
        } catch (Exception e) {
            return 0d;
        }
    }

    private double getExportSum(java.util.Date from, java.util.Date to) {
        try {
            java.util.ArrayList<model.PhieuXuat> list = PhieuXuatDAO.getInstance().selectAll();
            double sum = 0d;
            for (model.PhieuXuat p : list) {
                if (from == null || to == null || checkDate(p.getThoiGianTao(), from, to)) {
                    sum += p.getTongTien();
                }
            }
            return sum;
        } catch (Exception e) {
            return 0d;
        }
    }

    private java.util.Date[] getSelectedMonthRange(String monthStr, String yearStr) {
        if ((monthStr == null || monthStr.equals("Tất cả")) && (yearStr == null || yearStr.equals("Tất cả"))) {
            return new java.util.Date[] { null, null };
        }
        try {
            java.util.Calendar c = java.util.Calendar.getInstance();
            int year;
            if (yearStr == null || yearStr.equals("Tất cả")) {
                year = c.get(java.util.Calendar.YEAR);
            } else {
                year = Integer.parseInt(yearStr);
            }
            int month;
            if (monthStr == null || monthStr.equals("Tất cả")) {
                // cả năm
                c.set(java.util.Calendar.YEAR, year);
                c.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
                c.set(java.util.Calendar.DAY_OF_MONTH, 1);
                c.set(java.util.Calendar.HOUR_OF_DAY, 0);
                c.set(java.util.Calendar.MINUTE, 0);
                c.set(java.util.Calendar.SECOND, 0);
                c.set(java.util.Calendar.MILLISECOND, 0);
                java.util.Date from = c.getTime();

                c.set(java.util.Calendar.YEAR, year);
                c.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
                c.set(java.util.Calendar.DAY_OF_MONTH, 31);
                c.set(java.util.Calendar.HOUR_OF_DAY, 23);
                c.set(java.util.Calendar.MINUTE, 59);
                c.set(java.util.Calendar.SECOND, 59);
                c.set(java.util.Calendar.MILLISECOND, 999);
                java.util.Date to = c.getTime();
                return new java.util.Date[] { from, to };
            } else {
                month = Integer.parseInt(monthStr) - 1; // Calendar MONTH is 0-based
                c.set(java.util.Calendar.YEAR, year);
                c.set(java.util.Calendar.MONTH, month);
                c.set(java.util.Calendar.DAY_OF_MONTH, 1);
                c.set(java.util.Calendar.HOUR_OF_DAY, 0);
                c.set(java.util.Calendar.MINUTE, 0);
                c.set(java.util.Calendar.SECOND, 0);
                c.set(java.util.Calendar.MILLISECOND, 0);
                java.util.Date from = c.getTime();

                c.set(java.util.Calendar.DAY_OF_MONTH, c.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
                c.set(java.util.Calendar.HOUR_OF_DAY, 23);
                c.set(java.util.Calendar.MINUTE, 59);
                c.set(java.util.Calendar.SECOND, 59);
                c.set(java.util.Calendar.MILLISECOND, 999);
                java.util.Date to = c.getTime();
                return new java.util.Date[] { from, to };
            }
        } catch (Exception e) {
            return new java.util.Date[] { null, null };
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnResetThongKePr;
    private javax.swing.JTextField giaDen;
    private javax.swing.JTextField giaTu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBoxLuaChon;
    private javax.swing.JComboBox<String> jComboBoxLuaChon1;
    private com.toedter.calendar.JDateChooser jDateChooserFrom;
    private com.toedter.calendar.JDateChooser jDateChooserFromPr;
    private com.toedter.calendar.JDateChooser jDateChooserTo;
    private com.toedter.calendar.JDateChooser jDateChooserToPr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JTable tblTopUsers;
    private javax.swing.JScrollPane jScrollPaneTopUsers;
    private javax.swing.JPanel jPanelTopStaff;
    private javax.swing.JTable tblTopStaff;
    private javax.swing.JScrollPane jScrollPaneTopStaff;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldSearch1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel soLuong;
    private javax.swing.JTable tblAccount;
    private javax.swing.JTable tblPhieuNhap;
    private javax.swing.JTable tblThongKeProduct;
    private javax.swing.JLabel tongTien;
    private javax.swing.JTextField txtNamePr;
    private javax.swing.JLabel txtQuantityNcc;
    private javax.swing.JLabel txtQuantityProduct;
    private javax.swing.JLabel txtQuantityUser;
    // End of variables declaration//GEN-END:variables

    public void searchAllRepect() throws ParseException {
        String luaChon = jComboBoxLuaChon.getSelectedItem().toString();
        String content = jTextFieldSearch.getText();
        ArrayList<Phieu> result = null;

        switch (luaChon) {
            case "Tất cả":
                result = searchTatCa(content);
                break;
            case "Phiếu Nhập":
                result = searchPhieuNhap(content);
                break;
            case "Phiếu Xuất":
                result = searchPhieuXuat(content);
                break;
        }

        Iterator<Phieu> itr = result.iterator();
        if (jDateChooserFrom.getDate() != null || jDateChooserTo.getDate() != null) {
            Date from;
            Date to;
            if (jDateChooserFrom.getDate() != null && jDateChooserTo.getDate() == null) {
                from = ConvertDate.getInstance().ChangeFrom(jDateChooserFrom.getDate());
                to = ConvertDate.getInstance().ChangeTo(new Date());
                while (itr.hasNext()) {
                    Phieu phieu = itr.next();
                    if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                        itr.remove();
                    }
                }
            } else if (jDateChooserTo.getDate() != null && jDateChooserFrom.getDate() == null) {
                String sDate1 = "01/01/2022";
                from = ConvertDate.getInstance().ChangeFrom(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1));
                to = ConvertDate.getInstance().ChangeTo(jDateChooserTo.getDate());
                while (itr.hasNext()) {
                    Phieu phieu = itr.next();
                    if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                        itr.remove();
                    }
                }
            } else {
                from = ConvertDate.getInstance().ChangeFrom(jDateChooserFrom.getDate());
                to = ConvertDate.getInstance().ChangeTo(jDateChooserTo.getDate());
                while (itr.hasNext()) {
                    Phieu phieu = itr.next();
                    if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                        itr.remove();
                    }
                }
            }
        }

        ArrayList<Phieu> result1 = new ArrayList<>();
        if (giaTu.getText().length() > 0 || giaDen.getText().length() > 0) {
            double a;
            double b;

            if (giaTu.getText().length() > 0 && giaDen.getText().length() == 0) {
                a = Double.parseDouble(giaTu.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() >= a) {
                        result1.add(result.get(i));
                    }
                }
            } else if (giaTu.getText().length() == 0 && giaDen.getText().length() > 0) {
                ;
                b = Double.parseDouble(giaDen.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() <= b) {
                        result1.add(result.get(i));
                    }
                }
            } else if (giaTu.getText().length() > 0 && giaDen.getText().length() > 0) {
                a = Double.parseDouble(giaTu.getText());
                b = Double.parseDouble(giaDen.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() >= a && result.get(i).getTongTien() <= b) {
                        result1.add(result.get(i));
                    }
                }
            }
        }
        if (giaTu.getText().length() > 0 || giaDen.getText().length() > 0) {
            loadDataToTableSearch(result1);
            double sum = 0;
            for (Phieu phieu : result1) {
                sum += phieu.getTongTien();
            }
            soLuong.setText(result1.size() + "");
            tongTien.setText(formatter.format(sum) + "đ");
        } else {
            loadDataToTableSearch(result);
            double sum = 0;
            for (Phieu phieu : result) {
                sum += phieu.getTongTien();
            }
            soLuong.setText(result.size() + "");
            tongTien.setText(formatter.format(sum) + "đ");
        }
    }

    public void filterThongKeSanPham() throws ParseException {
        ArrayList<ThongKeProduct> thongKe = new ArrayList<>();
        if (jDateChooserFromPr.getDate() != null || jDateChooserToPr.getDate() != null) {
            Date from = new Date();
            Date to = new Date();
            if (jDateChooserFromPr.getDate() != null && jDateChooserToPr.getDate() == null) {
                from = jDateChooserFromPr.getDate();
                to = new Date();
            } else if (jDateChooserToPr.getDate() != null && jDateChooserFromPr.getDate() == null) {
                String sDate1 = "01/01/2025";
                from = ConvertDate.getInstance().ChangeFrom(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1));
                to = jDateChooserToPr.getDate();
            } else {
                from = jDateChooserFromPr.getDate();
                to = jDateChooserToPr.getDate();
                if (from.getTime() > to.getTime()) {
                    JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ !", "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    jDateChooserFrom.setCalendar(null);
                    jDateChooserTo.setCalendar(null);
                }
            }
            thongKe = ThongKeDAO.getInstance().getThongKe(from, to);

        } else {
            thongKe = ThongKeDAO.getInstance().getThongKe();
        }
        if (!txtNamePr.getText().equals("")) {
            thongKe = searchTenSanPhamThongKe(thongKe, txtNamePr.getText());
        }
        loadDataToTableThongKeProduct(thongKe);
    }

    private void loadDataToTableThongKeProduct(ArrayList<ThongKeProduct> thongKe) {
        try {
            DefaultTableModel tblModelAcc = (DefaultTableModel) tblThongKeProduct.getModel();
            tblModelAcc.setRowCount(0);
            for (int i = 0; i < thongKe.size(); i++) {
                tblModelAcc.addRow(new Object[] {
                        (i + 1), thongKe.get(i).getMaMay(), thongKe.get(i).getTenMay(), thongKe.get(i).getSlNhap(),
                        thongKe.get(i).getSlXuat()
                });
            }
            tblThongKeProduct.getColumnModel().getColumn(2).setPreferredWidth(400);
        } catch (Exception e) {
        }
    }

    private ArrayList<ThongKeProduct> searchTenSanPhamThongKe(ArrayList<ThongKeProduct> arr, String name) {
        ArrayList<ThongKeProduct> result = new ArrayList<>();
        for (ThongKeProduct i : arr) {
            if (i.getMaMay().toLowerCase().contains(name.toLowerCase())
                    || i.getTenMay().toLowerCase().contains(name.toLowerCase())) {
                result.add(i);
            }
        }
        return result;
    }

    private class PieChartPanel extends javax.swing.JPanel {
        private final Color importColor = new Color(76, 175, 80); // xanh lá
        private final Color exportColor = new Color(244, 67, 54); // đỏ
        private String selectedMonth = "Tất cả";
        private String selectedYear = "Tất cả";

        public void setMonthYearSelection(String month, String year) {
            this.selectedMonth = month == null ? "Tất cả" : month;
            this.selectedYear = year == null ? "Tất cả" : year;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            java.util.Date[] range = getSelectedMonthRange(selectedMonth, selectedYear);
            int importCount = (range[0] == null || range[1] == null)
                    ? getImportOrderCount()
                    : getImportOrderCount(range[0], range[1]);
            int exportCount = (range[0] == null || range[1] == null)
                    ? getExportOrderCount()
                    : getExportOrderCount(range[0], range[1]);
            int total = importCount + exportCount;

            int padding = 30;
            int legendHeight = 70;
            int size = Math.min(getWidth(), getHeight() - legendHeight) - 2 * padding;
            size = Math.max(size, 0);
            int x = (getWidth() - size) / 2;
            int y = padding;

            // Tiêu đề
            g2.setFont(getFont().deriveFont(Font.BOLD, 18f));
            String title = "Biểu đồ tròn: Đơn hàng Nhập/Xuất";
            int titleWidth = g2.getFontMetrics().stringWidth(title);
            g2.setColor(Color.DARK_GRAY);
            g2.drawString(title, (getWidth() - titleWidth) / 2, 24);

            if (total == 0 || size <= 0) {
                g2.setFont(getFont().deriveFont(Font.PLAIN, 14f));
                String noData = "Không có dữ liệu";
                int w = g2.getFontMetrics().stringWidth(noData);
                g2.setColor(Color.GRAY);
                g2.drawString(noData, (getWidth() - w) / 2, getHeight() / 2);
                g2.dispose();
                return;
            }

            double importAngle = 360.0 * importCount / total;
            double exportAngle = 360.0 - importAngle;

            // Vẽ nền tròn nhẹ
            g2.setColor(new Color(245, 245, 245));
            g2.fillOval(x - 6, y - 6, size + 12, size + 12);

            // Vẽ phần Nhập
            g2.setColor(importColor);
            g2.fill(new Arc2D.Double(x, y, size, size, 90, -importAngle, Arc2D.PIE));

            // Vẽ phần Xuất
            g2.setColor(exportColor);
            g2.fill(new Arc2D.Double(x, y, size, size, 90 - importAngle, -exportAngle, Arc2D.PIE));

            // Chú thích (legend)
            int legendY = y + size + 20;
            int box = 16;
            g2.setFont(getFont().deriveFont(Font.PLAIN, 14f));

            // Nhập
            g2.setColor(importColor);
            g2.fillRect(x, legendY, box, box);
            g2.setColor(Color.DARK_GRAY);
            String importLabel = String.format(" Nhập: %d (%.1f%%)", importCount, importCount * 100.0 / total);
            g2.drawString(importLabel, x + box + 6, legendY + box - 2);

            // Xuất
            int secondColX = x + Math.max(size / 2, 160);
            g2.setColor(exportColor);
            g2.fillRect(secondColX, legendY, box, box);
            g2.setColor(Color.DARK_GRAY);
            String exportLabel = String.format(" Xuất: %d (%.1f%%)", exportCount, exportCount * 100.0 / total);
            g2.drawString(exportLabel, secondColX + box + 6, legendY + box - 2);

            g2.dispose();
        }
    }
}
