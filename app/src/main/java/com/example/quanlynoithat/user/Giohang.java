package com.example.quanlynoithat.user;

public class Giohang {
    public String idSp;
    public String maSP;
    public String tenSp;
    public float giaSp;
    public String anhSp;
    public int sl;

    public Giohang(String idSp, String maSP, String tenSp, float giaSp, String anhSp, int sl) {
        this.idSp = idSp;
        this.maSP = maSP;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.anhSp = anhSp;
        this.sl = sl;
    }

    public String getIdSp() {
        return idSp;
    }

    public void setIdSp(String idSp) {
        this.idSp = idSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public float getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(float giaSp) {
        this.giaSp = giaSp;
    }

    public String getAnhSp() {
        return anhSp;
    }

    public void setAnhSp(String anhSp) {
        this.anhSp = anhSp;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
}
