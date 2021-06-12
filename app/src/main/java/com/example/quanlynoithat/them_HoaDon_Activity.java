package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.widget.Toolbar;

public class them_HoaDon_Activity extends AppCompatActivity {

    EditText edtMaHD,edtNgayHD;
    Button btnXacNhan,btnTroVe;
    Spinner spnMaKH,spnTrangThai;
    ArrayList<String> allMaKH = new ArrayList<>();
    ArrayList<String> allTrangThai = new ArrayList<>();
    String tempMaKH,tempTrangThai;
    ArrayAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_them_hoa_don);
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
                Intent intent = new Intent(them_HoaDon_Activity.this, hoaDon_activiy.class);
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
                if (edtMaHD.getText().length() == 0)
                {
                    thongBaoLoi("LỖI","Vui lòng nhập Mã Hóa Đơn");
                    return true;
                }
                if (edtNgayHD.getText().length() == 0)
                {
                    thongBaoLoi("LỖI","Vui lòng nhập Ngày Hóa Đơn");
                    return true;
                }
                kiemTraTrung();
                return true;
            }
            case R.id.AB_huy:
            {
                Intent intent = new Intent(them_HoaDon_Activity.this, hoaDon_activiy.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setEvent()
    {
        loadKhachHang();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allMaKH);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnMaKH.setAdapter(adapter);
        spnMaKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempMaKH = allMaKH.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter adapterTrangThai = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allTrangThai);
        adapterTrangThai.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnTrangThai.setAdapter(adapterTrangThai);
        spnTrangThai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempTrangThai = allTrangThai.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (edtMaHD.getText().length() == 0)
                {
                    thongBaoLoi("LỖI","Vui lòng nhập Mã Hóa Đơn");
                    return;
                }
                if (edtNgayHD.getText().length() == 0)
                {
                    thongBaoLoi("LỖI","Vui lòng nhập Ngày Hóa Đơn");
                    return;
                }
                kiemTraTrung();
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(them_HoaDon_Activity.this, hoaDon_activiy.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControl() {
        btnXacNhan = findViewById(R.id.btnXacNhan);
        edtMaHD = findViewById(R.id.edtMaHD);
        edtNgayHD = findViewById(R.id.edtNgayHD);
        btnTroVe = findViewById(R.id.btnHuy);
        spnMaKH = findViewById(R.id.spnMaKH);
        spnTrangThai = findViewById(R.id.spnTrangThai);
        allTrangThai.add("Chờ xác nhận");
        allTrangThai.add("Xác nhận");
        allTrangThai.add("Đang giao");
        allTrangThai.add("Đã thanh toán");
        toolbar = findViewById(R.id.toolbarfrmThemHoaDon);
    }
    private void kiemTraTrung() {
        hoaDon lsp = getHoaDon();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<hoaDon>> call = method.checkHoaDon(lsp.getMaHD());
        call.enqueue(new Callback<ArrayList<hoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<hoaDon>> call, Response<ArrayList<hoaDon>> response) {
                Log.v("phan hoi",response.body().toString());
                if(response.body().size()>0)
                {
                    thongBaoLoi("Lỗi","Mã hóa đơn đã tồn tại");
                    return;
                }
                else
                    HDNhap();
            }

            @Override
            public void onFailure(Call<ArrayList<hoaDon>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }
    private void HDNhap() {
        hoaDon hoaDon = getHoaDon();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<hoaDon> call = method.saveHoaDon(hoaDon);
        call.enqueue(new Callback<hoaDon>() {
            @Override
            public void onResponse(Call<hoaDon> call, Response<hoaDon> response) {
                Log.v("phan hoi",response.body().toString());
                thongbao("THÊM THÀNH CÔNG","Bạn có Xác nhận thoát chương trình không?");
            }

            @Override
            public void onFailure(Call<hoaDon> call, Throwable t) {
                thongbao("THÊM THẤT BẠI","Bạn có Xác nhận thoát chương trình không?");
                Log.v("phan hoi","fail");
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
                Intent intent = new Intent(them_HoaDon_Activity.this, hoaDon_activiy.class);
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

    private hoaDon getHoaDon() {
        hoaDon hoaDon = new hoaDon();
        hoaDon.setMaHD(edtMaHD.getText().toString());
        hoaDon.setSdt(tempMaKH);
        hoaDon.setNgayThem(edtNgayHD.getText().toString());
        hoaDon.setTinhTrang(tempTrangThai);
        return hoaDon;
    }
    private void loadKhachHang() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<khachhang>> call = method.getKhachHang();
        call.enqueue(new Callback<ArrayList<khachhang>>() {
            @Override
            public void onResponse(Call<ArrayList<khachhang>> call, Response<ArrayList<khachhang>> response) {
                allMaKH.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    allMaKH.add(response.body().get(i).getSdtKh());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<khachhang>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }
}