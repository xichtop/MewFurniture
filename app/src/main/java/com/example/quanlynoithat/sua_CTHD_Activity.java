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

public class sua_CTHD_Activity extends AppCompatActivity {
    Spinner spnMaHD, spnMaSP;
    EditText edtSoLuong;
    Button btnXacNhanThem, btnTroVe;
    String id,tempMaHD, tempMaSP,soLuong;
    ArrayList<String> allMaHD = new ArrayList<>();
    ArrayList<sanpham> allMaSP = new ArrayList<>();
    sanpham tempSP;
    ArrayAdapter adapterHoaDon;
    CustomSpinnerAdapter adapter1;
    Toolbar toolbar;
    int v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_sua_chi_tiet_hoa_don);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        actionBar();
        spnMaHD.setEnabled(false);
        spnMaSP.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sua_CTHD_Activity.this, cthd_activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AB_xacNhan: {
                if (edtSoLuong.getText().toString().length() == 0) {
                    thongBaoLoi("LỖI","Vui lòng nhập số lượng");
                    return true;
                }
                if (Integer.parseInt(edtSoLuong.getText().toString()) <= 0) {
                    thongBaoLoi("LỖI","Vui lòng nhập số lượng lớn hơn 0");
                    return true;
                }
                CTHDSua();
                return true;
            }
            case R.id.AB_huy: {
                Intent intent = new Intent(sua_CTHD_Activity.this, cthd_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        btnXacNhanThem = findViewById(R.id.btnXacNhan);
        spnMaHD = findViewById(R.id.spnMaHD);
        spnMaSP = findViewById(R.id.spnMaSP);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        btnTroVe = findViewById(R.id.btnHuy);
        toolbar = findViewById(R.id.toolbarfrmSuaChiTietHoaDon);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        tempMaHD = bundle.getString("maHD");
        tempMaSP = bundle.getString("maSP");
        soLuong = String.valueOf(bundle.getInt("soLuong"));
        v=bundle.getInt("v");
        edtSoLuong.setText(soLuong);

    }


    private void setEvent() {
        loadDataMaHD();
        adapterHoaDon = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allMaHD);
        adapterHoaDon.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnMaHD.setAdapter(adapterHoaDon);
        loadDataSanPham();
        adapter1 = new CustomSpinnerAdapter(getApplicationContext(), allMaSP);
        spnMaSP.setAdapter(adapter1);

        spnMaSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempSP = allMaSP.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnXacNhanThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoLuong.getText().toString().length() == 0) {
                    thongBaoLoi("LỖI","Vui lòng nhập số lượng");
                    return;
                }
                if (Integer.parseInt(edtSoLuong.getText().toString()) <= 0) {
                    thongBaoLoi("LỖI","Vui lòng nhập số lượng lớn hơn 0");
                    return;
                }
                CTHDSua();
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sua_CTHD_Activity.this, cthd_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void CTHDSua() {
        cthd cthd = getCTHD();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<cthd> call = method.updateCTHD(cthd.getId(),cthd);
        call.enqueue(new Callback<cthd>() {
            @Override
            public void onResponse(Call<cthd> call, Response<cthd> response) {
                Log.v("phan hoi",response.body().toString());
                thongbao("SỬA THÀNH CÔNG","Bạn có Xác nhận thoát chương trình không?");
            }

            @Override
            public void onFailure(Call<cthd> call, Throwable t) {
                thongbao("SỬA THẤT BẠI","Bạn có Xác nhận thoát chương trình không?");
                Log.v("phan hoi","fail");
            }
        });
    }

    public void thongbao(String title, String mes) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(sua_CTHD_Activity.this, cthd_activity.class);
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
    private void loadDataMaHD() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<hoaDon>> call = method.getHoaDon();
        call.enqueue(new Callback<ArrayList<hoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<hoaDon>> call, Response<ArrayList<hoaDon>> response) {
                allMaHD.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    allMaHD.add(response.body().get(i).getMaHD());
                }
                for(int i=0;i<allMaHD.size();i++)
                {
                    if(allMaHD.get(i).equals(tempMaHD))
                    {
                        spnMaHD.setSelection(i);
                        break;
                    }
                }
                adapterHoaDon.notifyDataSetChanged();
                for(int i=0;i<allMaHD.size();i++)
                {
                    if(allMaHD.get(i).equals(tempMaHD))
                    {
                        spnMaHD.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<hoaDon>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });

    }
    private void loadDataSanPham() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<sanpham>> call = method.getSanPham();
        call.enqueue(new Callback<ArrayList<sanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<sanpham>> call, Response<ArrayList<sanpham>> response) {
                allMaSP.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    allMaSP.add(response.body().get(i));
                }
                for(int i=0;i<allMaSP.size();i++)
                {
                    if(allMaSP.get(i).getMaSP().equals(tempMaSP))
                    {
                        spnMaSP.setSelection(i);
                        break;
                    }
                }
                adapter1.notifyDataSetChanged();
                for(int i=0;i<allMaSP.size();i++)
                {
                    if(allMaSP.get(i).getMaSP().equals(tempMaSP))
                    {
                        spnMaSP.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<sanpham>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });

    }
    private cthd getCTHD() {
        cthd c = new cthd();
        c.setId(id);
        c.setMaHD(tempMaHD);
        c.setMaSP(tempSP.getMaSP());
        c.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString()));
        c.setV(v);
        return c;
    }
}
