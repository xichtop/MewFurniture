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
import com.example.quanlynoithat.sanpham;
import com.example.quanlynoithat.cthd;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chiTietHoaDon extends AppCompatActivity {
    Toolbar toolbarCTHD;
    ListView listViewCTHD;

//    View footerView;
//    boolean isLoading = false;
//    mHandler mHandler;

    cthdKH_Adapter cthdAdapter;
    ArrayList<sanpham> mangSP = new ArrayList<>();
    ArrayList<cthd> mangCTHD = new ArrayList<>();
    ArrayList<cthd_SP> mangCTHD_KH;
    String maHD ="";
    //sanpham temp=null;
    //ArrayList<sanpham> dataTemp = new ArrayList<>();

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
        GetMaHD();
        ActionTollbar();
        loadDataCTHD();
//        loadDataSP();
//        loadData();

//        listViewCTHD.setOnItemClickListener((parent, view, position, id) -> {
//            Intent intent = new Intent(getApplicationContext(),chitietsanpham.class);
//            intent.putExtra("thongtinsp",mangSP_theoloai.get(position));
//            startActivity(intent);
//            finish();
//        });
    }

    private void loadDataCTHD() {
        method method = RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<cthd>> call = method.getCTHDByHD(maHD);
        call.enqueue(new Callback<ArrayList<cthd>>() {
            @Override
            public void onResponse(Call<ArrayList<cthd>> call, Response<ArrayList<cthd>> response) {
                mangCTHD.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    mangCTHD.add(response.body().get(i));
                }
                loadDataSP();
            }
            @Override
            public void onFailure(Call<ArrayList<cthd>> call, Throwable t) {
                Log.v("phan hoi", t.toString());
            }
        });
    }

    private void loadDataSP() {
        method method = RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<sanpham>> call = method.getSanPham();
        call.enqueue(new Callback<ArrayList<sanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<sanpham>> call, Response<ArrayList<sanpham>> response) {
                mangSP.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    mangSP.add(response.body().get(i));
                }
                loadData();
            }
            @Override
            public void onFailure(Call<ArrayList<sanpham>> call, Throwable t) {
                Log.v("phan hoi", t.toString());
            }
        });
    }

    private void loadData() {
        mangCTHD_KH.clear();
        for (int i = 0; i < mangCTHD.size(); i++)
        {
            for (int j = 0; j < mangSP.size(); j++)
            {
                if (mangCTHD.get(i).getMaSP().equals(mangSP.get(j).getMaSP()))
                {
                    cthd_SP cs = new cthd_SP(mangSP.get(j).getTenSP(), mangSP.get(j).getGiaSP(),
                            mangCTHD.get(i).getSoLuong(), mangSP.get(j).getUrl());
                    mangCTHD_KH.add(cs);
                    Log.e("mang", cs.getTenSP());
                    break;
                }
            }
        }
        cthdAdapter.notifyDataSetChanged();
    }

    private void ActionTollbar() {
        setSupportActionBar(toolbarCTHD);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCTHD.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetMaHD() {
        maHD = getIntent().getStringExtra("maHD");
        Log.d("ten loai sP: ", maHD);
        toolbarCTHD.setTitle("Chi Tiết " + maHD);
    }

    private void setControl() {
        toolbarCTHD = findViewById(R.id.ToolBarsanphamtheoloai);
        listViewCTHD = findViewById(R.id.ListViewSanPhamTheoloai);
        mangCTHD_KH = new ArrayList<>();
        cthdAdapter  = new cthdKH_Adapter(getApplicationContext(), mangCTHD_KH);
        listViewCTHD.setAdapter(cthdAdapter);
    }
}
