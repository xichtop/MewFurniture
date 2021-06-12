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

public class loaiSanPham_activity extends AppCompatActivity {
    private SwipeMenuListView lvLoaiSP;
    //private ListView lvLoaiSP;
    ArrayList<loaisanpham> data = new ArrayList<>();
    ArrayList<loaisanpham> dataSearch = new ArrayList<>();
    ArrayList<loaisanpham> dataTemp = new ArrayList<>();
    loaisanphamAdapter adapter = null;
    EditText timKiem;
    Button btnThem,btnXoa,btnSua,btnTroVe,btnSearch;
    loaisanpham temp =null;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_loai_san_pham);
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
                Intent intent = new Intent(loaiSanPham_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.Ab_them:
            {
                Intent intent = new Intent(loaiSanPham_activity.this, them_Loaisanpham_Activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(loaiSanPham_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setControl() {
        timKiem = findViewById(R.id.edtTimkiem);
        lvLoaiSP = findViewById(R.id.lvLoaiSP);
        btnThem = findViewById(R.id.btnThem);
        btnTroVe = findViewById(R.id.btnTroVe);
        toolbar = findViewById(R.id.toolbarfrmLoaiSanPham);
    }
    private void loadData()
    {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<loaisanpham>> call = method.getLoaiSanPham();
        call.enqueue(new Callback<ArrayList<loaisanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<loaisanpham>> call, Response<ArrayList<loaisanpham>> response) {
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
            public void onFailure(Call<ArrayList<loaisanpham>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }
    private void timKiem(String tenLoaiSP)
    {
        // clear data search ( ghi đè chứ không ghi nối tiếp)
        dataSearch.clear();
        // đưa dữ liệu vào  xem nhu list tạm đẻ lưu
        for(int i=0;i<dataTemp.size();i++)
        {
            if(dataTemp.get(i).getTenLoaiSP().toLowerCase().contains(tenLoaiSP)==true)
            {
                dataSearch.add(dataTemp.get(i));
            }
        }
        // clear data( ghi đè chứ không ghi nối tiếp)
        data.clear();
        // đưa dữ liệu vao để hien thi
        for(int i=0;i<dataSearch.size();i++)
        {
            data.add(dataSearch.get(i));
        }
        adapter.notifyDataSetChanged();
        if(data.size()>0)
            temp=data.get(0);
    }
    public void thongbaoXoa(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.v("loai",temp.getMaLoaiSP());
                method method= RitrofitClient.getRetrofit().create(method.class);
                Call<loaisanpham> call = method.deleteLoaiSanPham(temp.getId());
                call.enqueue(new Callback<loaisanpham>() {
                    @Override
                    public void onResponse(Call<loaisanpham> call, Response<loaisanpham> response) {
                        thongBaoLoi("XÓA THÀNH CÔNG", "Đã xóa thành công");
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<loaisanpham> call, Throwable t) {
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
        adapter = new loaisanphamAdapter(this,R.layout.listview_loai_san_pham,data);
        lvLoaiSP.setAdapter(adapter);

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
                // set item title
                //openItem.setTitle("Open");
                openItem.setIcon(R.drawable.ic_action_edit);
                // set item title fontsize
                //openItem.setTitleSize(18);
                // set item title font color
                //openItem.setTitleColor(Color.WHITE);
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
                // set a icon
                //deleteItem.setIcon(R.drawable.ic_action_update);
                deleteItem.setTitle("Del");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        lvLoaiSP.setMenuCreator(creator);

        adapter.notifyDataSetChanged();

        loadData();

        lvLoaiSP.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                temp = data.get(position);
                switch (index)
                {
                    case 0:
                    {
                        Intent intent = new Intent(loaiSanPham_activity.this, sua_Loaisanpham_Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",temp.getId());
                        bundle.putString("id",temp.getId());
                        bundle.putString("maLoaiSP",temp.getMaLoaiSP());
                        bundle.putString("tenLoaiSP",temp.getTenLoaiSP());
                        bundle.putString("mieuta",temp.getMieuta());
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
                }
                return false;
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loaiSanPham_activity.this, them_Loaisanpham_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loaiSanPham_activity.this, MainActivity.class);
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
                    //lvLoaiSP.setAdapter(null);
                    lvLoaiSP.setAdapter(adapter);
                    timKiem(s.toString().toLowerCase());
                    adapter.notifyDataSetChanged();
                } else {
                    //lvLoaiSP.setAdapter(null);
                    lvLoaiSP.setAdapter(adapter);
                    data.clear();
                    loadData();
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void kiemTraXoa() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<sanpham>> call = method.checkDeleteLSP(temp.getTenLoaiSP());
        call.enqueue(new Callback<ArrayList<sanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<sanpham>> call, Response<ArrayList<sanpham>> response) {
                if(response.body().size()<=0)
                {
                    thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa loại sản phẩm?");
                }
                else
                {
                    thongBaoLoi("XÓA THẤT BẠI", "Loại sản phẩm đã có sản phẩm, không thể xóa");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<sanpham>> call, Throwable t) {
                thongBaoLoi("XÓA THẤT BẠI", "Thao tác thất bại");

            }
        });
    }
}
