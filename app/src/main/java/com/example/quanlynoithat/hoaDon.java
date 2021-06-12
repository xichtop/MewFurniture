package com.example.quanlynoithat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class hoaDon {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("maHD")
    @Expose
    private String maHD;
    @SerializedName("sdt")
    @Expose
    private String sdt;
    @SerializedName("ngayThem")
    @Expose
    private String ngayThem;
    @SerializedName("tinhTrang")
    @Expose
    private String tinhTrang;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public hoaDon() {
    }

    public hoaDon(String id, String maHD, String sdt, String ngayThem, String tinhTrang, Integer v) {
        this.id = id;
        this.maHD = maHD;
        this.sdt = sdt;
        this.ngayThem = ngayThem;
        this.tinhTrang = tinhTrang;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNgayThem() {
        return ngayThem;
    }

    public void setNgayThem(String ngayThem) {
        this.ngayThem = ngayThem;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
