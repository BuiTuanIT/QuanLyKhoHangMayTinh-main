/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Tran Nhat Sinh
 */
public class PhieuXuat extends Phieu {

    private String companyName;

    public PhieuXuat() {
    }

    public PhieuXuat(String maPhieu, Timestamp thoiGianTao, String nguoiTao, ArrayList<ChiTietPhieu> CTPhieu,
            double tongTien) {
        this(maPhieu, thoiGianTao, nguoiTao, CTPhieu, tongTien, null);
    }

    public PhieuXuat(String maPhieu, Timestamp thoiGianTao, String nguoiTao, ArrayList<ChiTietPhieu> CTPhieu,
            double tongTien, String companyName) {
        super(maPhieu, thoiGianTao, nguoiTao, CTPhieu, tongTien);
        this.companyName = companyName;
    }

    public PhieuXuat(String maPhieu, Timestamp thoiGianTao, String nguoiTao, double tongTien, String companyName) {
        super(maPhieu, thoiGianTao, nguoiTao, tongTien);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
