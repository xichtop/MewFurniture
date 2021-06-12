package com.example.quanlynoithat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ketQua_doanhThu_Activity  extends AppCompatActivity {
    private ListView lvTKDT;
    ArrayList<doanhThu> data = new ArrayList<>();
    doanhThuAdapter adapter = null;
    Button btnTroVe,btnBieuDo;
    String thoiGian="",thang="",nam="";
    ArrayList<cthd> dataCTHD = new ArrayList<>();
    ArrayList<hoaDon> dataHoaDon = new ArrayList<>();
    ArrayList<sanpham> dataSanPham = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_ket_qua_doanh_thu);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.them_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xacNhan:
            {
                Intent intent = new Intent(ketQua_doanhThu_Activity.this, bieu_do_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("thang",thang);
                bundle.putString("nam",nam);
                ArrayList<String> tenSP = new ArrayList<>();
                ArrayList<String> doanhThu = new ArrayList<>();
                for(int i=0;i<data.size();i++)
                {
                    tenSP.add(data.get(i).getTenNT());
                    doanhThu.add(String.valueOf(data.get(i).getDoanhThu()));
                }
                bundle.putStringArrayList("tenSP",tenSP);
                bundle.putStringArrayList("doanhThu",doanhThu);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(ketQua_doanhThu_Activity.this, thongTin_DoanhThu_Activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setControl() {
        btnTroVe = findViewById(R.id.btnTroVe);
        btnBieuDo = findViewById(R.id.btnBieuDo);
        lvTKDT = findViewById(R.id.lvDoanhThu);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            thang = bundle.getString("thang");
            nam = bundle.getString("nam");
            thoiGian += thang;
            thoiGian += "/";
            thoiGian += nam;
        }
    }
    private void setEvent() {
        adapter = new doanhThuAdapter(this,R.layout.listview_doanh_thu,data);
        lvTKDT.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadCTHD();

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ketQua_doanhThu_Activity.this, thongTin_DoanhThu_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ketQua_doanhThu_Activity.this, bieu_do_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("thang",thang);
                bundle.putString("nam",nam);
                ArrayList<String> tenSP = new ArrayList<>();
                ArrayList<String> doanhThu = new ArrayList<>();
                for(int i=0;i<data.size();i++)
                {
                    tenSP.add(data.get(i).getTenNT());
                    doanhThu.add(String.valueOf(data.get(i).getDoanhThu()));
                }
                bundle.putStringArrayList("tenSP",tenSP);
                bundle.putStringArrayList("doanhThu",doanhThu);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadCTHD() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<cthd>> call = method.getCTHD();
        call.enqueue(new Callback<ArrayList<cthd>>() {
            @Override
            public void onResponse(Call<ArrayList<cthd>> call, Response<ArrayList<cthd>> response) {
                dataCTHD.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    dataCTHD.add(response.body().get(i));
                }
                loadHoaDon();
            }

            @Override
            public void onFailure(Call<ArrayList<cthd>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }
    private void loadHoaDon()
    {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<hoaDon>> call = method.getHoaDon();
        call.enqueue(new Callback<ArrayList<hoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<hoaDon>> call, Response<ArrayList<hoaDon>> response) {
                dataHoaDon.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    dataHoaDon.add(response.body().get(i));
                }
                loadSanPham();
            }

            @Override
            public void onFailure(Call<ArrayList<hoaDon>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }
    private void loadSanPham()
    {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<sanpham>> call = method.getSanPham();
        call.enqueue(new Callback<ArrayList<sanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<sanpham>> call, Response<ArrayList<sanpham>> response) {
                dataSanPham.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    dataSanPham.add(response.body().get(i));
                }
                loadData();
            }

            @Override
            public void onFailure(Call<ArrayList<sanpham>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }

    private void loadData() {
        ArrayList<String> maHD = new ArrayList<>();
        ArrayList<cthd> maCTHD = new ArrayList<>();
        Map<String,Integer> newMaCTHD = new HashMap<String,Integer>();
        //lay ra cac ma hoa don thoa
        for(int i=0;i<dataHoaDon.size();i++)
        {
            if(dataHoaDon.get(i).getNgayThem().contains(thoiGian)==true)
            {
                maHD.add(dataHoaDon.get(i).getMaHD());
            }
        }
        //lay ra cac chi tiet hoa don thoa
        for(int i=0;i<maHD.size();i++)
        {
            for(int j=0;j<dataCTHD.size();j++)
            {
                if(maHD.get(i).equals(dataCTHD.get(j).getMaHD()))
                {
                    maCTHD.add(dataCTHD.get(j));
                }
            }
        }
        //gop cac gia tri trung nhau
        for(int i=0;i<maCTHD.size();i++)
        {
            if(newMaCTHD.containsKey(maCTHD.get(i).getMaSP())){
                newMaCTHD.put(maCTHD.get(i).getMaSP(),newMaCTHD.get(maCTHD.get(i).getMaSP()) + maCTHD.get(i).getSoLuong());
            }
            else {
                newMaCTHD.put(maCTHD.get(i).getMaSP(), maCTHD.get(i).getSoLuong());
            }
        }
        for (String maSP : newMaCTHD.keySet()) {
            for(int j=0;j<dataSanPham.size();j++)
            {
                if(maSP.equals(dataSanPham.get(j).getMaSP()))
                {
                    doanhThu dt = new doanhThu();
                    dt.setMaNT(dataSanPham.get(j).getMaSP());
                    dt.setTenNT(dataSanPham.get(j).getTenSP());
                    dt.setSoLuong(newMaCTHD.get(maSP));
                    dt.setDoanhThu(newMaCTHD.get(maSP)*dataSanPham.get(j).getGiaSP());
                    dt.setThoiGian(thoiGian);
                    data.add(dt);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}