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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.widget.Toolbar;

public class hoaDon_activiy extends AppCompatActivity {
    Button btnThem,btnXoa,btnSua,btnTroVe, btnDS;
    //private ListView lvHoaDon;
    private SwipeMenuListView lvHoaDon;
    ArrayList<hoaDon> data = new ArrayList<>();
    ArrayList<hoaDon> dataSearch = new ArrayList<>();
    ArrayList<hoaDon> dataTemp = new ArrayList<>();
    hoaDonAdapter adapter = null;
    EditText timKiem;
    hoaDon temp=null;
    String sdtKH = "";
    TextView tieuDeHD;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_hoa_don);
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
                if (sdtKH == "")
                {
                    Intent intent = new Intent(hoaDon_activiy.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(hoaDon_activiy.this, khachHang_activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xemDS:
            {
                Intent intent = new Intent(hoaDon_activiy.this, cthd_activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("maHD", temp.getMaHD());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.Ab_them:
            {
                Intent intent = new Intent(hoaDon_activiy.this, them_HoaDon_Activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_troVe:
            {
                if (sdtKH == "")
                {
                    Intent intent = new Intent(hoaDon_activiy.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(hoaDon_activiy.this, khachHang_activity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        btnThem = findViewById(R.id.btnThem);
        btnTroVe = findViewById(R.id.btnTroVe);
        lvHoaDon = findViewById(R.id.lvHoaDon);
        timKiem = findViewById(R.id.edtTimKiem);
        tieuDeHD = findViewById(R.id.txtHoaDon);
        toolbar = findViewById(R.id.toolbarfrmHoaDon);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            sdtKH = bundle.getString("sdtKH");
        }
    }
    private void setEvent() {
        adapter = new hoaDonAdapter(this,R.layout.listview_hoa_don,data);
        lvHoaDon.setAdapter(adapter);

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
                // set item title font color
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

                SwipeMenuItem xacNhanHD = new SwipeMenuItem(
                        getApplicationContext());
                xacNhanHD.setBackground(new ColorDrawable(Color.rgb(197,
                        141, 64)));
                xacNhanHD.setWidth(170);
                xacNhanHD.setIcon(R.drawable.ic_action_done);
                menu.addMenuItem(xacNhanHD);
            }
        };
        lvHoaDon.setMenuCreator(creator);

        adapter.notifyDataSetChanged();

        //loadData();
        if(sdtKH=="") // load tat ca
        {
            loadData();
        }
        else // load hoa don cua khach hang
        {
            loadDataMaKH();
            tieuDeHD.setText(tieuDeHD.getText()+ " Của Khách Hàng Có SĐT "+ sdtKH);
            tieuDeHD.setTextSize(15);
        }


        lvHoaDon.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                temp = data.get(position);
                switch (index)
                {
                    case 0:
                    {
                        Intent intent = new Intent(hoaDon_activiy.this, sua_HoaDon_Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",temp.getId());
                        bundle.putString("mahd",temp.getMaHD());
                        bundle.putString("sdt",temp.getSdt());
                        Log.e("sdt", temp.getSdt());
                        bundle.putString("ngayThem",temp.getNgayThem());
                        bundle.putString("tinhtrang",temp.getTinhTrang());
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
                        Intent intent = new Intent(hoaDon_activiy.this, cthd_activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("maHD", temp.getMaHD());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case 3:
                    {
                        if (temp.getTinhTrang().equals("Xác nhận"))
                        {
                            thongBaoLoi("XÁC NHẬN HÓA ĐƠN", "Hóa đơn này đã được xác nhận!");
                        }
                        else
                        {
                            thongBaoXanNhanHD("XÁC NHẬN HÓA ĐƠN", "Xác nhận hóa đơn này?");
                        }
                        break;
                    }
                }
                return false;
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hoaDon_activiy.this, them_HoaDon_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sdtKH == "")
                {
                    Intent intent = new Intent(hoaDon_activiy.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(hoaDon_activiy.this, khachHang_activity.class);
                    startActivity(intent);
                    finish();
                }
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
                    //lvHoaDon.setAdapter(null);
                    lvHoaDon.setAdapter(adapter);
                    timKiem(s.toString());
                    adapter.notifyDataSetChanged();
                } else {
                    //lvHoaDon.setAdapter(null);
                    lvHoaDon.setAdapter(adapter);
                    data.clear();
                    if (sdtKH == "")
                    {
                        loadData();
                    }
                    else
                    {
                        loadDataMaKH();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void loadDataMaKH() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<hoaDon>> call = method.getHDByKH(sdtKH);
        call.enqueue(new Callback<ArrayList<hoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<hoaDon>> call, Response<ArrayList<hoaDon>> response) {
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
            public void onFailure(Call<ArrayList<hoaDon>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }

    private void loadData()
    {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<hoaDon>> call = method.getHoaDon();
        call.enqueue(new Callback<ArrayList<hoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<hoaDon>> call, Response<ArrayList<hoaDon>> response) {
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
            public void onFailure(Call<ArrayList<hoaDon>> call, Throwable t) {
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
            if(dataTemp.get(i).getMaHD().toLowerCase().contains(tenLoaiSP)==true)
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
    public void thongbaoXoa(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                method method= RitrofitClient.getRetrofit().create(method.class);
                Call<hoaDon> call = method.deleteHoaDon(temp.getId());
                call.enqueue(new Callback<hoaDon>() {
                    @Override
                    public void onResponse(Call<hoaDon> call, Response<hoaDon> response) {
                        thongBaoLoi("XÓA THÀNH CÔNG", "Đã xóa thành công");
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<hoaDon> call, Throwable t) {
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

    public void thongBaoXanNhanHD(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                temp.setTinhTrang("Xác nhận");
                method method= RitrofitClient.getRetrofit().create(method.class);
                Call<hoaDon> call = method.updateHoaDon(temp.getId(),temp);
                call.enqueue(new Callback<hoaDon>() {
                    @Override
                    public void onResponse(Call<hoaDon> call, Response<hoaDon> response) {
                        Log.v("phan hoi",response.body().toString());
                        thongBaoLoi("XÁC NHẬN HÓA ĐƠN","Đã xác nhận hóa đơn!");
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<hoaDon> call, Throwable t) {
                        thongBaoLoi("XÁC NHẬN HÓA ĐƠN","Lỗi xác nhận hóa đơn!");
                        Log.v("phan hoi","fail");
                    }
                });
            }
        });
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
    private void kiemTraXoa() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<cthd>> call = method.checkDeleteHoaDon(temp.getMaHD());
        call.enqueue(new Callback<ArrayList<cthd>>() {
            @Override
            public void onResponse(Call<ArrayList<cthd>> call, Response<ArrayList<cthd>> response) {
                if(response.body().size()<=0)
                {
                    thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa hóa đơn?");
                }
                else
                {
                    thongBaoLoi("XÓA THẤT BẠI", "Hóa đơn đã có chi tiết hóa đơn, không thể xóa");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<cthd>> call, Throwable t) {
                thongBaoLoi("XÓA THẤT BẠI", "Thao tác thất bại");
            }
        });
    }
}
