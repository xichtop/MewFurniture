package com.example.quanlynoithat.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.RitrofitClient;
import com.example.quanlynoithat.cthd_activity;
import com.example.quanlynoithat.khachhang;
import com.example.quanlynoithat.method;
import com.example.quanlynoithat.sua_CTHD_Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Thongtinkhachhang extends AppCompatActivity {
    EditText edtHoTenKh, edtSdt,edtDiaChi,edtEmail;
    Button btnXacNhan,btnTrove;
    String sdtKh,hoTenKh,email,diaChiKh;
    String OTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        setControl();
        setEvent();
    }
    private void setControl() {
        edtHoTenKh = findViewById(R.id.edittextTenkh);
        edtSdt = findViewById(R.id.edittextSDTkh);
        edtDiaChi = findViewById(R.id.edittextDiaChikh);
        edtEmail = findViewById(R.id.edittextEmailkh);
        btnXacNhan = findViewById(R.id.buttonXacnhan);
        btnTrove = findViewById(R.id.buttonTrove);
        edtSdt.setEnabled(false);
        hoTenKh = trangchu.hoTenKh;
        diaChiKh = trangchu.diaChiKh;
        sdtKh = trangchu.sdtKh;
        email = trangchu.email;
        edtSdt.setText(sdtKh);
        edtEmail.setText(email);
        edtHoTenKh.setText(hoTenKh);
        edtDiaChi.setText(diaChiKh);
    }

    private void setEvent() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtHoTenKh.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p H??? T??n Kh??ch H??ng!");
                    return;
                }
                if (edtDiaChi.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p ?????a Ch???!");
                    return;
                }
                if (edtEmail.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p Email!");
                    return;
                }
                guiOTP();
            }
        });
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thongtinkhachhang.this, trangchu.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void thongBaoLoi(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // N??t Ok
        b.setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //T???o dialog
        AlertDialog al = b.create();
        //Hi???n th???
        al.show();
    }
    public void thongbao(String title, String mes) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // N??t Ok
        b.setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Thongtinkhachhang.this, xacNhanThanhToan.class);
                Bundle bundle = new Bundle();
                bundle.putString("OTP",OTP);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        //N??t Cancel
        b.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //T???o dialog
        AlertDialog al = b.create();
        //Hi???n th???
        al.show();
    }
    private void guiOTP() {
        khachhang kh = new khachhang();
        kh.setEmail(edtEmail.getText().toString().trim());
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<String> call = method.getXacNhanOTP(kh);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                OTP = response.body();
                thongbao("M?? OTP ???? ???????c g???i","Vui l??ng nh???p OTP");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("err", t.toString());
                thongBaoLoi("L???i","G???i m?? OTP th???t b???i");
            }
        });
    }

}