package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.widget.Toolbar;

public class them_Khachhang_Activity extends AppCompatActivity {

    EditText edtSDTKh, edtHoTenKh, edtDiaChiKh, edtEmail, edtPassword;
    Button btnXacNhanThemKh,btnTroVeKh;
    RadioButton rbAdmin, rbUser;
    Toolbar toolbar;
    Boolean kiemTraTrung=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_them_khach_hang);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        actionBar();
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
                Intent intent = new Intent(them_Khachhang_Activity.this, khachHang_activity.class);
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
                if (edtSDTKh.getText().length() != 10 )
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Số Điện Thoại đúng 10 số!");
                    return true;
                }
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
                if (edtPassword.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Mật Khẩu!");
                    return true;
                }
                kiemTraTrung();
                return true;
            }
            case R.id.AB_huy:
            {
                Intent intent = new Intent(them_Khachhang_Activity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setEvent()
    {
        btnXacNhanThemKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (edtSDTKh.getText().length() != 10 )
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Số Điện Thoại đúng 10 số!");
                    return;
                }
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
                kiemTraTrung();
            }
        });
        btnTroVeKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(them_Khachhang_Activity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControl() {
        btnXacNhanThemKh = findViewById(R.id.btnXacNhan);
        edtSDTKh = findViewById(R.id.edtSDT);
        edtHoTenKh = findViewById(R.id.edthoTenKH);
        edtDiaChiKh = findViewById(R.id.edtDiaChi);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        rbAdmin = findViewById(R.id.rbAdmin);
        rbUser = findViewById(R.id.rbUser);
        btnTroVeKh = findViewById(R.id.btnHuy);
        toolbar = findViewById(R.id.toolbarfrmThemKhachhang);
    }
    private void kiemTraTrung() {
        khachhang lsp = getKhachhang();
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
                    KHNhap();
            }

            @Override
            public void onFailure(Call<ArrayList<khachhang>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }
    private void KHNhap() {
        khachhang kh = getKhachhang();
        method method = RitrofitClient.getRetrofit().create(method.class);
        Call<khachhang> call = method.saveKhachHang(kh);
        call.enqueue(new Callback<khachhang>() {
            @Override
            public void onResponse(Call<khachhang> call, Response<khachhang> response) {
                Log.v("phan hoi", response.body().toString());
                thongbao("THÊM THÀNH CÔNG", "Bạn có Xác nhận thoát chương trình không?");
            }

            @Override
            public void onFailure(Call<khachhang> call, Throwable t) {
                thongbao("THÊM THẤT BẠI", "Bạn có Xác nhận thoát chương trình không?");
                Log.v("phan hoi", "fail");
            }
        });
    }

    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(them_Khachhang_Activity.this, khachHang_activity.class);
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
        return kh;
    }
}
