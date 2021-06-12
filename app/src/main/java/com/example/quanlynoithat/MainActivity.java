package com.example.quanlynoithat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.quanlynoithat.user.xacNhanThanhToan;

import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    LinearLayout btnSP,btnLSP,btnKH,btnHD,btnCTHD,btnTK;
    Button btnSanPham,btnLoaiSanPham,btnKhachHang,btnHoaDon,btnChiTietHoaDon,btnThongKe;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        actionBar();
    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_SanPham:
            {
                Intent intent = new Intent(MainActivity.this, sanPham_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_LoaiSP:
            {
                Intent intent = new Intent(MainActivity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_KhachHang:
            {
                Intent intent = new Intent(MainActivity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_HoaDon:
            {
                Intent intent = new Intent(MainActivity.this, hoaDon_activiy.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_CTHD:
            {
                Intent intent = new Intent(MainActivity.this, cthd_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_ThongKe:
            {
                Intent intent = new Intent(MainActivity.this, thongTin_DoanhThu_Activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_DangXuat:
            {
                Intent intent = new Intent(MainActivity.this, dangNhap_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        btnSanPham = findViewById(R.id.btnSanPham);
        btnLoaiSanPham = findViewById(R.id.btnLoaiSanPham);
        btnKhachHang = findViewById(R.id.btnKhachHang);
        btnHoaDon = findViewById(R.id.btnHoaDon);
        btnChiTietHoaDon = findViewById(R.id.btnChiTietHoaDon);
        btnThongKe = findViewById(R.id.btnThongKe);

        btnSP = findViewById(R.id.btnSP);
        btnLSP = findViewById(R.id.btnLsp);
        btnKH = findViewById(R.id.btnKH);
        btnHD = findViewById(R.id.btnHD);
        btnCTHD = findViewById(R.id.btnCTHD);
        btnTK = findViewById(R.id.btnTK);
        toolbar = findViewById(R.id.toolbarfrmMainActivity);
    }

    private void setEvent() {
        btnSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, sanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, sanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLoaiSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, khachHang_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, hoaDon_activiy.class);
                startActivity(intent);
                finish();
            }
        });
        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, hoaDon_activiy.class);
                startActivity(intent);
                finish();
            }
        });
        btnCTHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cthd_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnChiTietHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cthd_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, thongTin_DoanhThu_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, thongTin_DoanhThu_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}