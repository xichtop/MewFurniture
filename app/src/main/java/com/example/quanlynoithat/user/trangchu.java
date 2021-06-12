package com.example.quanlynoithat.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.ConsoleMessage;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.RitrofitClient;
import com.example.quanlynoithat.dangNhap_activity;
import com.example.quanlynoithat.hoaDon_activiy;
import com.example.quanlynoithat.khachHang_activity;
import com.example.quanlynoithat.loaisanpham;
import com.example.quanlynoithat.method;
import com.example.quanlynoithat.sanpham;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.Console;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class trangchu extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;

    ArrayList<loaisanpham> mangLoaiSP;
    ArrayList<sanpham> mangSP;

    ArrayList<loaisanpham> dataTemp = new ArrayList<>();
    ArrayList<sanpham> dataSPTemp = new ArrayList<>();

    loaisanphamAdapter_client loaiSPadapter ;
    sanphamAdapter_client sanphamAdapter;
    loaisanpham temp =null;
    sanpham temp2=null;

    public static String sdtKh,hoTenKh,email,diaChiKh;

    public static ArrayList<Giohang> mangGiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_shopping);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        actionBar();
        actionviewFlipper();
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
                return true;
            }
            case R.id.menuDangXuat:
            {
                sdtKh ="";
                hoTenKh ="";
                email ="";
                diaChiKh = "";
                mangGiohang.clear();
                Intent intent = new Intent(getApplicationContext(), dangNhap_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        getdulieuLSP();
        getdulieuSP();
        CatchonItemListview();
    }

    private void getKhachHang() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        hoTenKh= bundle.getString("hoTenKh");
        sdtKh= bundle.getString("sdtKh");
        diaChiKh= bundle.getString("diaChiKh");
        email= bundle.getString("email");
    }

    private void CatchonItemListview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {

                    Intent intent = new Intent(trangchu.this, trangchu.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if (position == mangLoaiSP.size()-1)
                {
                    Log.e("sdt", sdtKh);
                    Intent intent = new Intent(trangchu.this, hoaDon_User.class);
                    intent.putExtra("sdtKH", sdtKh);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    Intent intent = new Intent(trangchu.this,sanphamActivity.class);
                    intent.putExtra("loaiSP",mangLoaiSP.get(position).getTenLoaiSP());
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void actionviewFlipper() {
        ArrayList<String> mangAdvertising = new ArrayList<>();
        mangAdvertising.add("https://bit.ly/34o1rQz");
        mangAdvertising.add("https://bit.ly/3wGBg3I");
        mangAdvertising.add("https://bit.ly/2SAa0VG");
        mangAdvertising.add("https://bit.ly/2Ti7Eem");
        for (int i=0;i<mangAdvertising.size();i++)
        {
            ImageView imgView = new ImageView(getApplicationContext());
            Picasso.get().load(mangAdvertising.get(i)).into(imgView);
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imgView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_iright);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_oright);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }


    private void setControl() {
        toolbar =findViewById(R.id.toolbarMain);
        viewFlipper = findViewById(R.id.viewFlipperMain);
        recyclerView = findViewById(R.id.recycleMain);
        navigationView = findViewById(R.id.NavigationMian);
        listView = findViewById(R.id.listviewMain);
        drawerLayout = findViewById(R.id.drawerLayoutMain);
        mangLoaiSP = new ArrayList<>();
        mangLoaiSP.add(0,new loaisanpham("-1","-1","Trang chủ","0",0));
        loaiSPadapter = new loaisanphamAdapter_client(mangLoaiSP,getApplicationContext());
        listView.setAdapter(loaiSPadapter);

        mangSP = new ArrayList<>();
        sanphamAdapter = new sanphamAdapter_client(getApplicationContext(),mangSP);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamAdapter);

        if(mangGiohang!=null){ }
        else{
            mangGiohang=new ArrayList<>();
        }
        if(hoTenKh==null)
        {
            hoTenKh="";
            sdtKh="";
            email="";
            diaChiKh="";
            getKhachHang();
        }
    }

    private void actionBar() {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

    }
    private void getdulieuLSP()
    {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<loaisanpham>> call = method.getLoaiSanPham();
        call.enqueue(new Callback<ArrayList<loaisanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<loaisanpham>> call, Response<ArrayList<loaisanpham>> response) {
                for(int i=0;i<response.body().size();i++)
                {
                    mangLoaiSP.add(response.body().get(i));
                }
                mangLoaiSP.add(new loaisanpham("-2", "-2", "Hóa Đơn", "0", -1));
                //loaiSPadapter.notifyDataSetChanged();
                loaiSPadapter.notifyDataSetChanged();
                if (mangLoaiSP.size() > 0)
                {
                    temp = mangLoaiSP.get(0);
                }
                dataSPTemp.clear();
                for(int i=0;i<mangLoaiSP.size();i++)
                {
                    dataTemp.add(mangLoaiSP.get(i));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<loaisanpham>> call, Throwable t) {
                Log.v("Phan hoi",t.toString());
            }
        });
    }
    private void getdulieuSP() {
        method method = RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<sanpham>> call = method.getSanPham();
        call.enqueue(new Callback<ArrayList<sanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<sanpham>> call, Response<ArrayList<sanpham>> response) {
                mangSP.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    mangSP.add(response.body().get(i));
                }
                sanphamAdapter.notifyDataSetChanged();
                if (mangSP.size() > 0) {
                    temp2 = mangSP.get(0);
                }
                dataSPTemp.clear();
                for (int i = 0; i < mangSP.size(); i++) {
                    dataSPTemp.add(mangSP.get(i));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<sanpham>> call, Throwable t) {
                Log.v("Phan hoi", t.toString());
            }
        });
    }
}
