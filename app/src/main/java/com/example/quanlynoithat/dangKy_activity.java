package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dangKy_activity extends AppCompatActivity {
    Button btnHuy,btnDangKy;
    EditText edtSDT,edtHoTen,edtDiaChi,edtEmail,edtMatKhau,edtReMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_dang_ky);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
    }

    private void setControl() {
        btnDangKy = findViewById(R.id.btnDangKy);
        btnHuy = findViewById(R.id.btnHuy);
        edtSDT = findViewById(R.id.edtSDT);
        edtHoTen = findViewById(R.id.edthoTenKH);
        edtEmail = findViewById(R.id.edtEmail);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtMatKhau = findViewById(R.id.edtPassword);
        edtReMatKhau = findViewById(R.id.edtRePassword);
    }

    private void setEvent() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSDT.getText().toString().trim().length()==0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Số điện thoại");
                    return;
                }
                if(edtHoTen.getText().toString().trim().length()==0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Họ và tên");
                    return;
                }
                if(edtDiaChi.getText().toString().trim().length()==0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập địa chỉ");
                    return;
                }
                if(edtEmail.getText().toString().trim().length()==0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập email");
                    return;
                }
                if(edtMatKhau.getText().toString().trim().length()==0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập mật khẩu");
                    return;
                }
                if(edtMatKhau.getText().toString().trim().length()<6)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập mật khẩu lớn hơn 6 ký tự");
                    return;
                }
                if(edtReMatKhau.getText().toString().trim().length()==0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập lại mật khẩu");
                    return;
                }
                if(edtReMatKhau.getText().toString().trim().length()<6)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập lại mật khẩu lớn hơn 6 ký tự");
                    return;
                }
                if(!edtMatKhau.getText().toString().trim().equals(edtReMatKhau.getText().toString().trim()))
                {
                    thongBaoLoi("Lỗi","Xác nhận mât khẩu không chính xác");
                    return;
                }
                kiemTraTrung();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangKy_activity.this, dangNhap_activity.class);
                startActivity(intent);
            }
        });
    }

    private void kiemTraTrung() {
        khachhang lsp = getKhachHang();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<khachhang>> call = method.checkKhachHang(lsp.getSdtKh());
        call.enqueue(new Callback<ArrayList<khachhang>>() {
            @Override
            public void onResponse(Call<ArrayList<khachhang>> call, Response<ArrayList<khachhang>> response) {
                Log.v("phan hoi",response.body().toString());
                if(response.body().size()>0)
                {
                    thongBaoLoi("Lỗi","Số điện thoại đã tồn tại");
                    return;
                }
                else
                    dangKy();
            }

            @Override
            public void onFailure(Call<ArrayList<khachhang>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }

    private void dangKy() {
        khachhang kh = getKhachHang();
        method method = RitrofitClient.getRetrofit().create(method.class);
        Call<khachhang> call = method.saveKhachHang(kh);
        call.enqueue(new Callback<khachhang>() {
            @Override
            public void onResponse(Call<khachhang> call, Response<khachhang> response) {
                Log.v("phan hoi", response.body().toString());
                thongbao("Đăng ký thành công", "Trở về form đăng nhập");
            }

            @Override
            public void onFailure(Call<khachhang> call, Throwable t) {
                thongBaoLoi("Đăng ký thất bại", "Đăng ký thất bại, vui lòng kiểm tra và thao tác lại");
                Log.v("phan hoi", "fail");
            }
        });
    }

    private khachhang getKhachHang() {
        khachhang kh = new khachhang();
        kh.setSdtKh(edtSDT.getText().toString());
        kh.setHotenKh(edtHoTen.getText().toString());
        kh.setDiachiKh(edtDiaChi.getText().toString());
        kh.setEmail(edtEmail.getText().toString());
        kh.setPassword(edtMatKhau.getText().toString());
        kh.setRole("User");
        return kh;
    }

    public void thongBaoLoi(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();
    }
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(dangKy_activity.this, dangNhap_activity.class);
                startActivity(intent);
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();
    }
}
