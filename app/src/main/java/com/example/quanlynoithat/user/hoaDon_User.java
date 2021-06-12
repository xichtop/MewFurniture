package com.example.quanlynoithat.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.RitrofitClient;
import com.example.quanlynoithat.dangNhap_activity;
import com.example.quanlynoithat.method;
import com.example.quanlynoithat.hoaDon;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class hoaDon_User extends AppCompatActivity {
    Toolbar toolbarHD;
    ListView listViewHD;

    hoaDonKhachHangAdapter hdAdapter;
    ArrayList<hoaDon> mangHD;
    String sdtKH = "";
    hoaDon temp=null;
    ArrayList<hoaDon> dataTemp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham);
        setControl();
        setEvent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
            {
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.menuDangXuat:
            {
                trangchu.sdtKh ="";
                trangchu.diaChiKh ="";
                trangchu.email ="";
                trangchu.hoTenKh = "";
                trangchu.mangGiohang.clear();
                Intent intent = new Intent(getApplicationContext(), dangNhap_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        GetHDKH();
        ActionTollbar();
//        loadData();
        listViewHD.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(),chiTietHoaDon.class);
            intent.putExtra("maHD", mangHD.get(position).getMaHD());
            startActivity(intent);
            //finish();
        });
    }

    private void loadData() {
        //GetHDKH();
        method method = RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<hoaDon>> call = method.getHDByKH(sdtKH);
        call.enqueue(new Callback<ArrayList<hoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<hoaDon>> call, Response<ArrayList<hoaDon>> response) {
                mangHD.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    mangHD.add(response.body().get(i));
                }
                hdAdapter.notifyDataSetChanged();
                if (mangHD.size() > 0) {
                    temp = mangHD.get(0);
                }
                dataTemp.clear();
                for (int i = 0; i < mangHD.size(); i++) {
                    dataTemp.add(mangHD.get(i));
                }
            }
            @Override
            public void onFailure(Call<ArrayList<hoaDon>> call, Throwable t) {
                Log.v("phan hoi", t.toString());
            }
        });
    }

    private void ActionTollbar() {
        setSupportActionBar(toolbarHD);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarHD.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetHDKH() {
        sdtKH= getIntent().getStringExtra("sdtKH");
        Log.d("sdtKH: ", sdtKH);
        loadData();
    }

    private void setControl() {
        toolbarHD = findViewById(R.id.ToolBarsanphamtheoloai);
        toolbarHD.setTitle("Hóa Đơn");
        listViewHD = findViewById(R.id.ListViewSanPhamTheoloai);
        mangHD = new ArrayList<>();
        hdAdapter  = new hoaDonKhachHangAdapter(getApplicationContext(),mangHD);
        listViewHD.setAdapter(hdAdapter);
    }
}
