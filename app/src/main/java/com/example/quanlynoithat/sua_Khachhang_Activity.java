package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.widget.Toolbar;

public class sua_Khachhang_Activity extends AppCompatActivity {

    EditText edtSDTKh, edtHoTenKh,edtDiaChiKh, edtEmail, edtPassword;
    RadioButton rbAdmin, rbUser;
    Button btnSuaKh,btnReload,btnExit;
    Toolbar toolbar;
    String id;
    int v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_sua_khach_hang);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        actionBar();
        edtSDTKh.setEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.them_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sua_Khachhang_Activity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xacNhan:
            {
                if (edtHoTenKh.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Họ Tên Khách Hàng!");
                    return true;
                }
                if (edtDiaChiKh.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Địa Chỉ!");
                    return true;
                }
                if (edtEmail.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Email!");
                    return true;
                }
                if (edtPassword.getText().length() == 0 )
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Mật Khẩu!");
                    return true;
                }
                Khsua();
                return true;
            }
            case R.id.AB_huy:
            {
                Intent intent = new Intent(sua_Khachhang_Activity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sua_Khachhang_Activity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSDTKh.setText("");
                edtHoTenKh.setText("");
                edtDiaChiKh.setText("");
                edtEmail.setText("");
                edtPassword.setText("");
            }
        });
        btnSuaKh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (edtHoTenKh.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Họ Tên Khách Hàng!");
                    return;
                }
                if (edtDiaChiKh.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Địa Chỉ!");
                    return;
                }
                if (edtEmail.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Email!");
                    return;
                }
                if (edtPassword.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Mật Khẩu!");
                    return;
                }
                Khsua();
            }
        });
    }

    private void Khsua() {
        khachhang kh = getKhachhang();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<khachhang> call = method.updateKhachHang(kh.getId(), kh);
        call.enqueue(new Callback<khachhang>() {
            @Override
            public void onResponse(Call<khachhang> call, Response<khachhang> response) {
                Log.v("phan hoi",response.body().toString());
                thongbao("HIỆU CHỈNH THÀNH CÔNG","Bạn có Xác nhận thoát chương trình không?");
            }

            @Override
            public void onFailure(Call<khachhang> call, Throwable t) {
                thongbao("HIỆU CHỈNH THẤT BẠI","Bạn có Xác nhận thoát chương trình không?");
                Log.v("phan hoi","fail");
            }
        });
    }

    private void setControl() {
        btnSuaKh = findViewById(R.id.btnXacNhan);
        edtSDTKh = findViewById(R.id.edtSDT);
        edtHoTenKh = findViewById(R.id.edtTenKH);
        edtDiaChiKh = findViewById(R.id.edtDiaChi);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        rbAdmin = findViewById(R.id.rbAdmin);
        rbUser = findViewById(R.id.rbUser);
        btnReload = findViewById(R.id.btnReload);
        btnExit = findViewById(R.id.btnHuy);
        toolbar = findViewById(R.id.toolbarfrmSuaKhachhang);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        edtSDTKh.setText(bundle.getString("sdtKh"));
        edtHoTenKh.setText(bundle.getString("hotenKh"));
        edtDiaChiKh.setText(bundle.getString("diachiKh"));
        edtEmail.setText(bundle.getString("email"));
        edtPassword.setText(bundle.getString("password"));
        if (bundle.getString("role").equalsIgnoreCase("Admin"))
        {
            rbAdmin.setChecked(true);
        }
        else
        {
            rbUser.setChecked(true);
        }
        v = bundle.getInt("v");
    }
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(sua_Khachhang_Activity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
            }
        });
        //Nút Cancel
        b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();
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

    private khachhang getKhachhang() {
        khachhang kh = new khachhang();
        kh.setId(id);
        kh.setSdtKh(edtSDTKh.getText().toString());
        kh.setHotenKh(edtHoTenKh.getText().toString());
        kh.setDiachiKh(edtDiaChiKh.getText().toString());
        kh.setEmail(edtEmail.getText().toString());
        kh.setPassword(edtPassword.getText().toString());
        if (rbAdmin.isChecked())
        {
            kh.setRole(rbAdmin.getText().toString());
        }
        else
        {
            kh.setRole(rbUser.getText().toString());
        }
        kh.setV(v);
        return kh;
    }
}
