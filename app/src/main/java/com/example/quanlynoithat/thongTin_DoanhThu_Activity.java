package com.example.quanlynoithat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class thongTin_DoanhThu_Activity extends AppCompatActivity {
    EditText edtNam;
    Button btnXacNhan,btnTroVe;
    Spinner spnThang;
    ArrayAdapter adapter;
    ArrayList<String> allThang = new ArrayList<>();
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_thong_tin_doanh_thu);
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
                Intent intent = new Intent(thongTin_DoanhThu_Activity.this, ketQua_doanhThu_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("thang",temp);
                bundle.putString("nam",edtNam.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(thongTin_DoanhThu_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setEvent() {
        addData();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allThang);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnThang.setAdapter(adapter);
        spnThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                temp = allThang.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thongTin_DoanhThu_Activity.this, ketQua_doanhThu_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("thang",temp);
                bundle.putString("nam",edtNam.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thongTin_DoanhThu_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void addData() {
        allThang.add("01");
        allThang.add("02");
        allThang.add("03");
        allThang.add("04");
        allThang.add("05");
        allThang.add("06");
        allThang.add("07");
        allThang.add("08");
        allThang.add("09");
        allThang.add("10");
        allThang.add("11");
        allThang.add("12");
    }

    private void setControl() {
        edtNam = findViewById(R.id.edtNam);
        spnThang = findViewById(R.id.spnThang);
        btnXacNhan = findViewById(R.id.btnThongKe);
        btnTroVe = findViewById(R.id.btnTroVe);
    }

}