package com.example.quanlynoithat;

import android.database.Observable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface method {
    @GET("api/loaisanpham")
    Call<ArrayList<loaisanpham>>getLoaiSanPham();

    @GET("api/loaisanpham/{id}")
    Call<ArrayList<loaisanpham>>getMotLoaiSanPham(@Path("id") String id);

    @POST("api/loaisanpham")
    Call<loaisanpham> saveLoaiSanPham(@Body loaisanpham lsp);

    @PUT("api/loaisanpham/{id}")
    Call<loaisanpham> updateLoaiSanPham(@Path("id") String id, @Body loaisanpham lsp);

    @DELETE("api/loaisanpham/{id}")
    Call<loaisanpham> deleteLoaiSanPham(@Path("id") String id);
///SAN PHAM
    @GET("api/sanpham")
    Call<ArrayList<sanpham>>getSanPham();

    @GET("api/sanpham/{id}")
    Call<ArrayList<sanpham>>getMotSanPham(@Path("id") String id);

    @GET("api/sanpham/spLoai/{loaiSP}")
    Call<ArrayList<sanpham>>getSanPham_theoloai(@Path("loaiSP") String loaiSP);

    @POST("api/sanpham")
    Call<sanpham> saveSanPham(@Body sanpham sp);

    @PUT("api/sanpham/{id}")
    Call<sanpham> updateSanPham(@Path("id") String id, @Body sanpham sp);

    @DELETE("api/sanpham/{id}")
    Call<sanpham> deleteSanPham(@Path("id") String id);

    //Khach Hang
    @GET("api/khachhang")
    Call<ArrayList<khachhang>>getKhachHang();

    @GET("api/khachhang/{id}")
    Call<ArrayList<khachhang>>getMotKhachHang(@Path("id") String id);

    @POST("api/khachhang")
    Call<khachhang> saveKhachHang(@Body khachhang kh);

    @PUT("api/khachhang/{id}")
    Call<khachhang> updateKhachHang(@Path("id") String id, @Body khachhang kh);

    @DELETE("api/khachhang/{id}")
    Call<khachhang> deleteKhachHang(@Path("id") String id);

    //HOA DON
    @GET("api/hoadon")
    Call<ArrayList<hoaDon>>getHoaDon();

    @GET("api/hoadon/{id}")
    Call<ArrayList<hoaDon>>getMotHoaDon(@Path("id") String id);

    @POST("api/hoadon")
    Call<hoaDon> saveHoaDon(@Body hoaDon kh);

    @PUT("api/hoadon/{id}")
    Call<hoaDon> updateHoaDon(@Path("id") String id, @Body hoaDon kh);

    @DELETE("api/hoadon/{id}")
    Call<hoaDon> deleteHoaDon(@Path("id") String id);

    //CHI TIET HOA DON
    @GET("api/chitiethoadon")
    Call<ArrayList<cthd>>getCTHD();

    @GET("api/chitiethoadon/{id}")
    Call<ArrayList<cthd>>getMotCTHD(@Path("id") String id);

    @POST("api/chitiethoadon")
    Call<cthd> saveCTHD(@Body cthd kh);

    @PUT("api/chitiethoadon/{id}")
    Call<cthd> updateCTHD(@Path("id") String id, @Body cthd kh);

    @DELETE("api/chitiethoadon/{id}")
    Call<cthd> deleteCTHD(@Path("id") String id);

    //KIEM TRA TRUNG
    @GET("api/loaisanpham/kiemtra/{id}")
    Call<ArrayList<loaisanpham>> checkLoaiSanPham(@Path("id") String id);

    @GET("api/sanpham/kiemtra/{id}")
    Call<ArrayList<sanpham>> checkSanPham(@Path("id") String id);

    @GET("api/khachhang/kiemtra/{id}")
    Call<ArrayList<khachhang>> checkKhachHang(@Path("id") String id);

    @GET("api/hoadon/kiemtra/{id}")
    Call<ArrayList<hoaDon>> checkHoaDon(@Path("id") String id);

    @GET("api/chitiethoadon/kiemtra/{maHD}/{maSP}")
    Call<ArrayList<cthd>> checkCTHD(@Path("maHD") String maHD,
                                  @Path("maSP") String maSP);
    //KIEM TRA XOA
    @GET("api/loaisanpham/kiemtraxoa/{id}")
    Call<ArrayList<sanpham>> checkDeleteLSP(@Path("id") String id);

    @GET("api/sanpham/kiemtraxoa/{id}")
    Call<ArrayList<hoaDon>> checkDeleteSanPham(@Path("id") String id);

    @GET("api/khachhang/kiemtraxoa/{id}")
    Call<ArrayList<hoaDon>> checkDeleteKhachHang(@Path("id") String id);

    @GET("api/hoadon/kiemtraxoa/{id}")
    Call<ArrayList<cthd>> checkDeleteHoaDon(@Path("id") String id);


    //DANG NHAP
    @POST("api/dangnhap")
    Call<ArrayList<khachhang>> dangNhap(@Body khachhang kh);

    // Lay cac CTHD cua 1 Hoa Don
    @GET("api/chitiethoadon/kiemtra/{maHD}")
    Call<ArrayList<cthd>> getCTHDByHD(@Path("maHD") String maHD);

    // Lay cac HD cua 1 Khach Hang
    @GET("api/dangnhap/{sdt}")
    Call<ArrayList<hoaDon>> getHDByKH(@Path("sdt") String sdt);

    //xac nhan don hang
    @POST("api/xacnhan")
    Call<String> getXacNhanOTP(@Body khachhang kh);

}
