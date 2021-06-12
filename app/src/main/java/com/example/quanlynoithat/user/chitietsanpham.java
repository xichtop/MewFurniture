package com.example.quanlynoithat.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class chitietsanpham extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imageViewChitiet;
    TextView tenSP,giaSP;
    Spinner spinner;
    Button btnDatmua;

    String id = "";
    String ten ="";
    String loaiSP="";
    float gia =0;
    String urlImage ="";
    String maSP="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
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
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        toolbarChitiet = findViewById(R.id.ToolBar_chitiet_sanphamtheoloai);
        imageViewChitiet = findViewById(R.id.imageview_ChitietSP);
        tenSP = findViewById(R.id.textviewTenSP_chitietSP);
        giaSP = findViewById(R.id.textviewGiaSP_chitietSP);
        spinner = findViewById(R.id.spinner_chitietSP);
        btnDatmua = findViewById(R.id.buttonThemgiohang);
    }

    private void setEvent() {
        ActionTollbar();
        getData();
        EventSpinner();
        Themgiohang();
    }

    private void Themgiohang() {
        btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trangchu.mangGiohang.size()>0){
                    int solg = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i=0;i<trangchu.mangGiohang.size();i++)
                    {
                        if(trangchu.mangGiohang.get(i).getIdSp().trim()==id.trim()){
                            trangchu.mangGiohang.get(i).setSl(trangchu.mangGiohang.get(i).getSl()+solg);
                            trangchu.mangGiohang.get(i).setGiaSp(gia*trangchu.mangGiohang.get(i).getSl());
                            exists = true;
                        }
                    }
                    if(exists==false)
                    {
                        solg = Integer.parseInt(spinner.getSelectedItem().toString());
                        float totalGia = solg*gia;
                        trangchu.mangGiohang.add(new Giohang(id,maSP,ten,totalGia,urlImage,solg));
                    }
                }else{
                    int solg = Integer.parseInt(spinner.getSelectedItem().toString());
                    float totalGia = solg*gia;
                    trangchu.mangGiohang.add(new Giohang(id,maSP,ten,totalGia,urlImage,solg));
                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void EventSpinner() {
        Integer[] soluong = new Integer[]{01,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31
        ,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71
        ,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getData() {
        sanpham sanpham = (com.example.quanlynoithat.sanpham) getIntent().getSerializableExtra("thongtinsp");
        id=sanpham.getId();
        ten = sanpham.getTenSP();
        gia = sanpham.getGiaSP();
        urlImage =sanpham.getUrl();
        loaiSP = sanpham.getLoaiSP();
        maSP = sanpham.getMaSP();

        tenSP.setText(ten);
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        giaSP.setText("Giá: " + decimalFormat.format(gia)+" Đ");
        Picasso.get().load(urlImage).into(imageViewChitiet);
    }

    private void ActionTollbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
