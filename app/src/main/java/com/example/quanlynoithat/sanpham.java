package com.example.quanlynoithat;

import android.widget.ImageView;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class sanpham implements Serializable{
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("maSP")
    @Expose
    private String maSP;
    @SerializedName("tenSP")
    @Expose
    private String tenSP;
    @SerializedName("giaSP")
    @Expose
    private float giaSP;
    @SerializedName("loaiSP")
    @Expose
    private String loaiSP;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public sanpham() {
    }

    public sanpham(String id, String maSP, String tenSP, float giaSP, String loaiSP, String url, Integer v) {
        this.id = id;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.loaiSP = loaiSP;
        this.url = url;
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public float getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(float giaSP) {
        this.giaSP = giaSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
