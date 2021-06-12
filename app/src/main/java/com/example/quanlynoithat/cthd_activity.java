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
public class cthd_activity extends AppCompatActivity {
    Button btnThem,btnXoa,btnSua,btnTroVe;
    //private ListView lvCTHD;
    private SwipeMenuListView lvCTHD;
    ArrayList<cthd> data = new ArrayList<>();
    ArrayList<cthd> dataSearch = new ArrayList<>();
    ArrayList<cthd> dataTemp = new ArrayList<>();
    ArrayList<String> allMaHD = new ArrayList<>();
    cthdAdapter adapter = null;
    EditText timKiem;
    TextView txtCTHD;
    cthd temp =null;
    String maHD = "";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_chi_tiet_hoa_don);
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
                if(maHD=="")
                {
                    Intent intent = new Intent(cthd_activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(cthd_activity.this, hoaDon_activiy.class);
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
                return true;
            }
            case R.id.Ab_them:
            {
                Intent intent = new Intent(cthd_activity.this, them_CTHD_Activity.class);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_troVe:
            {
                if(maHD=="")
                {
                    Intent intent = new Intent(cthd_activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(cthd_activity.this, hoaDon_activiy.class);
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
        lvCTHD = findViewById(R.id.lvCTHD);
        timKiem = findViewById(R.id.edtTimKiem);
        txtCTHD = findViewById(R.id.txtCTHD);
        toolbar = findViewById(R.id.toolbarfrmChiTietHoaDon);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            maHD = bundle.getString("maHD");
        }
    }
    private void setEvent() {
        adapter = new cthdAdapter(this,R.layout.listview_chi_tiet_hoa_don,data);
        lvCTHD.setAdapter(adapter);

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
                // set item icon
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
                // set a title
                deleteItem.setTitle("Del");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        lvCTHD.setMenuCreator(creator);

        adapter.notifyDataSetChanged();
        //loadData();

        if (maHD == "")
        {
            loadData();
        }
        else
        {
            loadDataMaHD();
            txtCTHD.setText(txtCTHD.getText() + " " + maHD);
            txtCTHD.setTextSize(30);
        }

        lvCTHD.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                temp = data.get(position);
                switch (index)
                {
                    case 0:
                    {
                        Intent intent = new Intent(cthd_activity.this, sua_CTHD_Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",temp.getId());
                        bundle.putString("maHD",temp.getMaHD());
                        bundle.putString("maSP",temp.getMaSP());
                        bundle.putInt("soLuong",temp.getSoLuong());
                        bundle.putInt("v",temp.getV());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case 1:
                    {
                        thongbaoXoa("XÁC NHẬN XÓA","Bạn chắc chắn muốn xóa chi tiết hóa đơn "+ temp.getMaHD()+" "+ temp.getSoLuong()+"?");
                        break;
                    }
                }
                return false;
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cthd_activity.this, them_CTHD_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maHD=="")
                {
                    Intent intent = new Intent(cthd_activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(cthd_activity.this, hoaDon_activiy.class);
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
                    //lvCTHD.setAdapter(null);
                    lvCTHD.setAdapter(adapter);
                    timKiem(s.toString());
                    adapter.notifyDataSetChanged();
                } else {
                    //lvCTHD.setAdapter(null);
                    lvCTHD.setAdapter(adapter);
                    data.clear();
                    if (maHD == "")
                    {
                        loadData();
                    }
                    else
                    {
                        loadDataMaHD();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void loadDataMaHD() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<cthd>> call = method.getCTHDByHD(maHD);
        call.enqueue(new Callback<ArrayList<cthd>>() {
            @Override
            public void onResponse(Call<ArrayList<cthd>> call, Response<ArrayList<cthd>> response) {
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
            public void onFailure(Call<ArrayList<cthd>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
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
                Call<cthd> call = method.deleteCTHD(temp.getId());
                call.enqueue(new Callback<cthd>() {
                    @Override
                    public void onResponse(Call<cthd> call, Response<cthd> response) {
                        thongBaoLoi("XÓA THÀNH CÔNG", "Đã xóa thành công");
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<cthd> call, Throwable t) {
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
    private void loadData() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<cthd>> call = method.getCTHD();
        call.enqueue(new Callback<ArrayList<cthd>>() {
            @Override
            public void onResponse(Call<ArrayList<cthd>> call, Response<ArrayList<cthd>> response) {
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
            public void onFailure(Call<ArrayList<cthd>> call, Throwable t) {
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
}
