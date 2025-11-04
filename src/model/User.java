/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;

public class User {

    private int id;
    private String companyName;
    private String address;
    private String phoneNumber;
    private Integer poin;

    public User() {
        super();
    }

    public User(int id, String companyName, String address, String phoneNumber, Integer poin) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.poin = poin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getPoin() {
        return poin;
    }

    public void setPoin(Integer poin) {
        this.poin = poin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.companyName);
        hash = 59 * hash + Objects.hashCode(this.address);
        hash = 59 * hash + Objects.hashCode(this.phoneNumber);
        hash = 59 * hash + Objects.hashCode(this.poin);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.companyName, other.companyName)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        return Objects.equals(this.poin, other.poin);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", companyName=" + companyName + ", address=" + address + ", phoneNumber="
                + phoneNumber + ", poin=" + poin + '}';
    }
}
