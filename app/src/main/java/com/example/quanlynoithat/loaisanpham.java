package com.example.quanlynoithat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class loaisanpham {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("maLoaiSP")
    @Expose
    private String maLoaiSP;
    @SerializedName("tenLoaiSP")
    @Expose
    private String tenLoaiSP;
    @SerializedName("mieuta")
    @Expose
    private String mieuta;

    public loaisanpham(String id, String maLoaiSP, String tenLoaiSP, String mieuta, Integer v) {
        this.id = id;
        this.maLoaiSP = maLoaiSP;
        this.tenLoaiSP = tenLoaiSP;
        this.mieuta = mieuta;
        this.v = v;
    }

    public loaisanpham() {
    }

    @SerializedName("__v")
    @Expose

    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(String maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getMieuta() {
        return mieuta;
    }

    public void setMieuta(String mieuta) {
        this.mieuta = mieuta;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}

