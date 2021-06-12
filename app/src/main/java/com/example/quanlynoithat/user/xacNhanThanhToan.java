package com.example.quanlynoithat.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlynoithat.MainActivity;
import com.example.quanlynoithat.R;
import com.example.quanlynoithat.RitrofitClient;
import com.example.quanlynoithat.cthd;
import com.example.quanlynoithat.dangNhap_activity;
import com.example.quanlynoithat.hoaDon;
import com.example.quanlynoithat.khachhang;
import com.example.quanlynoithat.method;
import com.example.quanlynoithat.sanPham_activity;
import com.example.quanlynoithat.sanpham;
import com.example.quanlynoithat.sua_SanPham_Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class xacNhanThanhToan extends AppCompatActivity {
    Button btnXacNhan,btnTroVe;
    EditText edtOTP1,edtOTP2,edtOTP3,edtOTP4,edtOTP5,edtOTP6;
    String OTP,maHD;
    hoaDon hd;
    ArrayList<hoaDon> data = new ArrayList<>();
    int vitri=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_xac_nhan_don_hang);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
    }
    private void setControl() {
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnTroVe = findViewById(R.id.btnTroVe);
        edtOTP1 = findViewById(R.id.edtOTP1);
        edtOTP2 = findViewById(R.id.edtOTP2);
        edtOTP3 = findViewById(R.id.edtOTP3);
        edtOTP4= findViewById(R.id.edtOTP4);
        edtOTP5 = findViewById(R.id.edtOTP5);
        edtOTP6 = findViewById(R.id.edtOTP6);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        OTP= bundle.getString("OTP");
    }
    private void setEvent() {
        edtOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    edtOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    edtOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    edtOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    edtOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    edtOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp ="";
                temp +=edtOTP1.getText().toString();
                temp +=edtOTP2.getText().toString();
                temp +=edtOTP3.getText().toString();
                temp +=edtOTP4.getText().toString();
                temp +=edtOTP5.getText().toString();
                temp +=edtOTP6.getText().toString();
                if(temp.equals(OTP))
                {
                    Log.v("xac nhan","TRUE");
                    loadData();
                }
                else
                {
                    thongBaoLoi("Lỗi","Mã xác thật không chính xác");
                }
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(xacNhanThanhToan.this, trangchu.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void loadData()
    {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<hoaDon>> call = method.getHoaDon();
        call.enqueue(new Callback<ArrayList<hoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<hoaDon>> call, Response<ArrayList<hoaDon>> response) {
                data.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    data.add(response.body().get(i));
                }
                luuHoaDon();
            }

            @Override
            public void onFailure(Call<ArrayList<hoaDon>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }
    private void luuHoaDon() {
        hd = new hoaDon();
        boolean check= true;
        String mahd="";
        while (check)
       {
           Random random = new Random();
           int value = random.nextInt((999 - 1) + 1) + 1;
            mahd = "HD" + value;
            int dem = 0;
           for(int i=0;i<data.size();i++)
           {
               if(!data.get(i).getMaHD().equals(mahd))
                   dem++;
           }
           if(dem==data.size())
           {
               check = false;
           }
       }

        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());

        hd.setMaHD(mahd);
        hd.setSdt(trangchu.sdtKh);
        hd.setTinhTrang("Chờ xác nhận");
        hd.setNgayThem(dateInString);

        HDNhap();

    }
    private void HDNhap() {

        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<hoaDon> call = method.saveHoaDon(hd);
        call.enqueue(new Callback<hoaDon>() {
            @Override
            public void onResponse(Call<hoaDon> call, Response<hoaDon> response) {
                if(response.body()!=null)
                {
                    maHD = response.body().getMaHD();
                    CTHDNhap();
                }
            }

            @Override
            public void onFailure(Call<hoaDon> call, Throwable t) {
                Log.v("phan hoi","fail");
            }
        });
    }

    private void CTHDNhap() {
        if(vitri<trangchu.mangGiohang.size())
        {
            cthd cthd = new cthd();
            cthd.setMaSP(trangchu.mangGiohang.get(vitri).getMaSP());
            cthd.setMaHD(maHD);
            cthd.setSoLuong(trangchu.mangGiohang.get(vitri).getSl());
            method method= RitrofitClient.getRetrofit().create(method.class);
            Call<cthd> call = method.saveCTHD(cthd);
            call.enqueue(new Callback<cthd>() {
                @Override
                public void onResponse(Call<cthd> call, Response<cthd> response) {
                    if(response.body()!=null)
                    {
                        vitri++;
                        CTHDNhap();
                    }

                }

                @Override
                public void onFailure(Call<cthd> call, Throwable t) {

                    Log.v("phan hoi","fail");
                }
            });
        }
        else
        {
            thongbao("Thành công","Đặt hàng thành công");
        }
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
                Intent intent = new Intent(xacNhanThanhToan.this, trangchu.class);
                startActivity(intent);
                finish();
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();
    }

}
