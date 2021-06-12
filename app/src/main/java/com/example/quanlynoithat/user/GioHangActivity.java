package com.example.quanlynoithat.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.RitrofitClient;
import com.example.quanlynoithat.khachhang;
import com.example.quanlynoithat.method;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangActivity extends AppCompatActivity {

    ListView lvGiohang;
    TextView thongbao;
    static TextView tongtien;
    Button thanhtoan,tieptucmua;
    Toolbar toolbarGiohang;
    giohangAdapter giohangAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        setControl();
        setEvent();
    }

    private void setEvent() {
        actionToolbar();
        checkData();
        getDulieuTotal();
        xoaSanPham();
        tieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),trangchu.class);
                startActivity(intent);
                finish();
            }
        });
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trangchu.mangGiohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),Thongtinkhachhang.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    thongbaothanhtoan("Thông báo: ","Bạn chưa có sản phẩm nào trong giỏ hàng");
                }
            }
        });
    }

    public void thongbaothanhtoan(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
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
    private void xoaSanPham() {
        lvGiohang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm khỏi giỏ hàng");
                builder.setMessage("Bạn có chắc xóa sản phẩm này ???");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(trangchu.mangGiohang.size()<=0){
                            thongbao.setVisibility(View.VISIBLE);
                        }
                        else{
                            trangchu.mangGiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            getDulieuTotal();
                            if(trangchu.mangGiohang.size()<=0){
                                thongbao.setVisibility(View.VISIBLE);
                            }
                            else{
                                thongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                getDulieuTotal();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        getDulieuTotal();
                    }
                });
                builder.show();
            }
        });

    }

    public static void getDulieuTotal() {
        float total=0;
        for(int i=0;i<trangchu.mangGiohang.size();i++)
        {
            total+=trangchu.mangGiohang.get(i).getGiaSp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(total)+" Đ");
    }

    private void checkData() {
        if(trangchu.mangGiohang.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            thongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);
        }
        else{
            giohangAdapter.notifyDataSetChanged();
            thongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);
        }
    }


    private void actionToolbar() {
        setSupportActionBar(toolbarGiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setControl() {
        lvGiohang = findViewById(R.id.ListviewGioHang);
        thongbao = findViewById(R.id.textviewAlertEmpty);
        tongtien = findViewById(R.id.textviewTongtien);
        thanhtoan=findViewById(R.id.buttonThanhToanGiohang);
        tieptucmua=findViewById(R.id.buttonTiepTucMuahang);
        toolbarGiohang=findViewById(R.id.ToolbarGioHang);
        giohangAdapter = new giohangAdapter(GioHangActivity.this,trangchu.mangGiohang);
        lvGiohang.setAdapter(giohangAdapter);
    }
}