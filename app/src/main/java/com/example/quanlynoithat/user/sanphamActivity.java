package com.example.quanlynoithat.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.RitrofitClient;
import com.example.quanlynoithat.dangNhap_activity;
import com.example.quanlynoithat.method;
import com.example.quanlynoithat.sanpham;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sanphamActivity extends AppCompatActivity {

    Toolbar toolbarSP_theoloai;
    ListView listViewSP_theoloai;

//    View footerView;
//    boolean isLoading = false;
//    mHandler mHandler;

    sanphamtheoloaiAdapter spTheoloaiAdapter;
    ArrayList<sanpham> mangSP_theoloai;
    String loaiSP ="";
    sanpham temp=null;
    ArrayList<sanpham> dataTemp = new ArrayList<>();
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
        GetloaiSP();
        ActionTollbar();
        loadData();
        listViewSP_theoloai.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(),chitietsanpham.class);
            intent.putExtra("thongtinsp",mangSP_theoloai.get(position));
            startActivity(intent);
            finish();
        });
    }

    private void loadData() {
        method method = RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<sanpham>> call = method.getSanPham_theoloai(loaiSP);
        call.enqueue(new Callback<ArrayList<sanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<sanpham>> call, Response<ArrayList<sanpham>> response) {
                mangSP_theoloai.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    mangSP_theoloai.add(response.body().get(i));
                }
                spTheoloaiAdapter.notifyDataSetChanged();
                if (mangSP_theoloai.size() > 0) {
                    temp = mangSP_theoloai.get(0);
                }
                dataTemp.clear();
                for (int i = 0; i < mangSP_theoloai.size(); i++) {
                    dataTemp.add(mangSP_theoloai.get(i));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<sanpham>> call, Throwable t) {
                Log.v("phan hoi", t.toString());
            }
        });
    }

    private void ActionTollbar() {
        setSupportActionBar(toolbarSP_theoloai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSP_theoloai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetloaiSP() {
        loaiSP= getIntent().getStringExtra("loaiSP");
        Log.d("ten loai sP: ", loaiSP);
    }

    private void setControl() {
        toolbarSP_theoloai = findViewById(R.id.ToolBarsanphamtheoloai);
        listViewSP_theoloai = findViewById(R.id.ListViewSanPhamTheoloai);
        mangSP_theoloai = new ArrayList<>();
        spTheoloaiAdapter  = new sanphamtheoloaiAdapter(getApplicationContext(),mangSP_theoloai);
        listViewSP_theoloai.setAdapter(spTheoloaiAdapter);

    }
}