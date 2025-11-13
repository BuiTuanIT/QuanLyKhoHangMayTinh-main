package model;

import java.util.Objects;

public class KhachHang {
    private int idUser;          // id_user trong database (PRIMARY KEY)
    private String companyName;  // companyname
    private String address;      // address
    private String phoneNumber;  // phone_number
    private int poin;            // poin

    // ===== Constructors =====
    public KhachHang() {}

    public KhachHang(int idUser, String companyName, String address, String phoneNumber, int poin) {
        this.idUser = idUser;
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.poin = poin;
    }

    // ===== Getters & Setters =====
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    // ===== Override equals() & hashCode() =====
    @Override
    public int hashCode() {
        // chỉ dựa theo khóa chính
        return Objects.hash(idUser);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        KhachHang other = (KhachHang) obj;
        return idUser == other.idUser;
    }

    // ===== toString() =====
    @Override
    public String toString() {
        return "KhachHang{" +
                "id=" + idUser +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", poin=" + poin +
                '}';
    }
}
