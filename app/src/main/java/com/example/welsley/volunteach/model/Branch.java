package com.example.welsley.volunteach.model;

/**
 * Created by Welsley on 8/11/2017.
 */

public class Branch {
    private int id;
    private String code;
    private String branchName;
    private String address;
    private String telephone;
    private String postalCode;

    public Branch(int id, String code, String branchName, String address, String telephone, String postalCode) {
        this.id = id;
        this.code = code;
        this.branchName = branchName;
        this.address = address;
        this.telephone = telephone;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
