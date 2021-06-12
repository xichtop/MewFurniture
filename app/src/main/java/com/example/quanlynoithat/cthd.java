package com.example.quanlynoithat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class cthd {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("maHD")
    @Expose
    private String maHD;
    @SerializedName("maSP")
    @Expose
    private String maSP;
    @SerializedName("soLuong")
    @Expose
    private Integer soLuong;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public cthd() {
    }

    public cthd(String id, String maHD, String maSP, Integer soLuong, Integer v) {
        this.id = id;
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
