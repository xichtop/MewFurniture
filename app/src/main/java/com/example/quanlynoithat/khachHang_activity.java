package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class khachHang_activity extends AppCompatActivity {

    //private ListView lvKhachhang;
    private SwipeMenuListView lvKhachhang;
    ArrayList<khachhang> data = new ArrayList<>();
    ArrayList<khachhang> dataSearch = new ArrayList<>();
    ArrayList<khachhang> dataTemp = new ArrayList<>();
    khachhangAdapter adapter = null;
    EditText timKiem;
    Button btnThem,btnXoa,btnSua,btnTroVe,btnDS;
    khachhang temp =null;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_khach_hang);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        actionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.form_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(khachHang_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xemDS:
            {
                Intent intent = new Intent(khachHang_activity.this, hoaDon_activiy.class);
                Bundle bundle = new Bundle();
                bundle.putString("maKH", temp.getSdtKh());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.Ab_them:
            {
                Intent intent = new Intent(khachHang_activity.this, them_Khachhang_Activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(khachHang_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setControl() {
        timKiem = findViewById(R.id.edtTimkiem);
        lvKhachhang = findViewById(R.id.lvKhachHang);
        btnThem = findViewById(R.id.btnThem);
        btnTroVe = findViewById(R.id.btnTroVe);
        btnDS = findViewById(R.id.btnDS);
        toolbar = findViewById(R.id.toolbarfrmKhachhang);
    }
    private void loadData()
    {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<khachhang>> call = method.getKhachHang();
        call.enqueue(new Callback<ArrayList<khachhang>>() {
            @Override
            public void onResponse(Call<ArrayList<khachhang>> call, Response<ArrayList<khachhang>> response) {
                data.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    data.add(response.body().get(i));
                }
                adapter.notifyDataSetChanged();
                if (data.size() > 0)
                {
                    temp = data.get(0);
                }
                dataTemp.clear();
                for(int i=0;i<data.size();i++)
                {
                    dataTemp.add(data.get(i));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<khachhang>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });

    }

    public void timKiem(String sdt)
    {
        dataSearch.clear();
        for (int i = 0; i < dataTemp.size(); i++)
        {
            if (dataTemp.get(i).getSdtKh().toLowerCase().contains(sdt) == true)
            {
                dataSearch.add(dataTemp.get(i));
            }
        }
        data.clear();
        for (int i = 0; i < dataSearch.size(); i++)
        {
            data.add(dataSearch.get(i));
        }
        adapter.notifyDataSetChanged();
        if (data.size() > 0)
        {
            temp = data.get(0);
        }
    }

    public void thongbaoXoa(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                method method= RitrofitClient.getRetrofit().create(method.class);
                Call<khachhang> call = method.deleteKhachHang(temp.getId());
                call.enqueue(new Callback<khachhang>() {
                    @Override
                    public void onResponse(Call<khachhang> call, Response<khachhang> response) {
                        thongBaoLoi("XÓA THÀNH CÔNG", "Đã xóa thành công");
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<khachhang> call, Throwable t) {
                        thongBaoLoi("XÓA THẤT BẠI", "Thao tác thất bại");
                        loadData();
                    }
                });

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

    private void setEvent() {
        adapter = new khachhangAdapter(this,R.layout.listview_khach_hang,data);
        lvKhachhang.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66,
                        0xff)));
                // set item width
                openItem.setWidth(170);
                // set a icon
                openItem.setIcon(R.drawable.ic_action_edit);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set item title
                deleteItem.setTitle("Del");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title fontsize
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);

                SwipeMenuItem xemDS = new SwipeMenuItem(
                        getApplicationContext());
                xemDS.setBackground(new ColorDrawable(Color.rgb(0x00,
                        0xff, 0x00)));
                xemDS.setWidth(170);
                xemDS.setIcon(R.drawable.ic_action_details);
                menu.addMenuItem(xemDS);
            }
        };
        lvKhachhang.setMenuCreator(creator);

        adapter.notifyDataSetChanged();

        loadData();


        lvKhachhang.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                temp = data.get(position);
                switch (index)
                {
                    case 0:
                    {
                        Intent intent = new Intent(khachHang_activity.this, sua_Khachhang_Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",temp.getId());
                        bundle.putString("sdtKh",temp.getSdtKh());
                        bundle.putString("hotenKh",temp.getHotenKh());
                        bundle.putString("diachiKh",temp.getDiachiKh());
                        bundle.putString("email",temp.getEmail());
                        bundle.putString("password",temp.getPassword());
                        bundle.putString("role",temp.getRole());
                        bundle.putInt("v",temp.getV());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case 1:
                    {
                       kiemTraXoa();
                        break;
                    }
                    case 2:
                    {
                        Intent intent = new Intent(khachHang_activity.this, hoaDon_activiy.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("sdtKH", temp.getSdtKh());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        break;
                    }
                }
                return false;
            }
        });

        btnDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(khachHang_activity.this, hoaDon_activiy.class);
                Bundle bundle = new Bundle();
                bundle.putString("sdtKH", temp.getSdtKh());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(khachHang_activity.this, them_Khachhang_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(khachHang_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        timKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s,int start, int count, int after){
            }

            @Override
            public void onTextChanged (CharSequence s,int start, int before, int count){
                //                nhaThuocActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged (Editable s){
                if (s.length() > 0) {
                    //lvKhachhang.setAdapter(null);
                    lvKhachhang.setAdapter(adapter);
                    timKiem(s.toString().toLowerCase());
                    adapter.notifyDataSetChanged();
                } else {
                    //lvKhachhang.setAdapter(null);
                    lvKhachhang.setAdapter(adapter);
                    data.clear();
                    loadData();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void kiemTraXoa() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<hoaDon>> call = method.checkDeleteKhachHang(temp.getSdtKh());
        call.enqueue(new Callback<ArrayList<hoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<hoaDon>> call, Response<ArrayList<hoaDon>> response) {
                if(response.body().size()<=0)
                {
                    thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa khách hàng?");
                }
                else
                {
                    thongBaoLoi("XÓA THẤT BẠI", "Khách hàng đã lập hóa đơn, không thể xóa");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<hoaDon>> call, Throwable t) {
                thongBaoLoi("XÓA THẤT BẠI", "Thao tác thất bại");
            }
        });
    }
}
