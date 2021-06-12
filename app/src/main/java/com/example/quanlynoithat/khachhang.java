package com.example.quanlynoithat;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class khachhang {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("sdt")
    @Expose
    private String sdtKh;
    @SerializedName("hoTen")
    @Expose
    private String hotenKh;
    @SerializedName("diaChi")
    @Expose
    private String diachiKh;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public khachhang() {
    }

    /*public khachhang(String sdtKh, String hotenKh, String diachiKh, String email, String password, String role) {
        this.sdtKh = sdtKh;
        this.hotenKh = hotenKh;
        this.diachiKh = diachiKh;
        this.email = email;
        this.password = password;
        this.role = role;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdtKh() {
        return sdtKh;
    }

    public void setSdtKh(String sdtKh) {
        this.sdtKh = sdtKh;
    }

    public String getHotenKh() {
        return hotenKh;
    }

    public void setHotenKh(String hotenKh) {
        this.hotenKh = hotenKh;
    }

    public String getDiachiKh() {
        return diachiKh;
    }

    public void setDiachiKh(String diachiKh) {
        this.diachiKh = diachiKh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    /*@Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("sdtKh", sdtKh);
        result.put("hotenKh", hotenKh);
        result.put("diachiKh", diachiKh);
        result.put("email", email);
        result.put("password", password);
        result.put("role", role);
        return result;
    }*/
}
