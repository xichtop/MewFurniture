package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.quanlynoithat.user.trangchu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dangNhap_activity extends AppCompatActivity {
    Button btnDangNhap,btnDangKy;
    CardView btnDN;
    EditText edtSDT,edtMatKhau;
    TextView btnDK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_dang_nhap);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
    }

    private void setControl() {
        //btnDangNhap = findViewById(R.id.btnDangNhap);
        //btnDangKy = findViewById(R.id.btnDangKy);
        edtSDT = findViewById(R.id.edtSDT);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDN = findViewById(R.id.btnDangNhap);
        btnDK = findViewById(R.id.btnDangKy);
    }

    private void setEvent() {
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSDT.getText().toString().trim().length()==0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Số điện thoại");
                    return;
                }
                if(edtMatKhau.getText().toString().trim().length()==0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập mật khẩu");
                    return;
                }
                dangNhap();
            }
        });
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangNhap_activity.this, dangKy_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void dangNhap() {
        khachhang kh = getKhachHang();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<khachhang>> call = method.dangNhap(kh);
        call.enqueue(new Callback<ArrayList<khachhang>>() {
            @Override
            public void onResponse(Call<ArrayList<khachhang>> call, Response<ArrayList<khachhang>> response) {
                if(response.body().size()>0)
                {
                    khachhang temp = response.body().get(0);
                    if(temp!=null)
                    {
                        if(temp.getRole().equals("User"))
                        {
                            Log.v("role","user");
                            //chuyen sang trang user
                            Intent intent = new Intent(dangNhap_activity.this, trangchu.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("sdtKh",temp.getSdtKh());
                            bundle.putString("hoTenKh",temp.getHotenKh());
                            bundle.putString("diaChiKh",temp.getDiachiKh());
                            bundle.putString("email",temp.getEmail());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                        else {
                            Log.v("role","Admin");
                            //chuyen sang trang admin
                            Intent intent = new Intent(dangNhap_activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                else
                    thongBaoLoi("Lỗi","Đăng nhập thất bại");
            }

            @Override
            public void onFailure(Call<ArrayList<khachhang>> call, Throwable t) {
                //thongBaoLoi("Lỗi","Đăng nhập thất bại");
                Log.v("errr",t.toString());
            }
        });
    }

    private khachhang getKhachHang() {
        khachhang kh = new khachhang();
        kh.setSdtKh(edtSDT.getText().toString());
        kh.setPassword(edtMatKhau.getText().toString());
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
}
