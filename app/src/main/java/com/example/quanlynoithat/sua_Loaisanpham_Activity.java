package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.widget.Toolbar;

public class sua_Loaisanpham_Activity extends AppCompatActivity {
    EditText edtmaLoaiSP, edtTenLoaiSP,edtMieuta;
    Button btnSuaLoaiSP,btnExit;
    String maLoaiSP,tenLoaiSP,mieuta,id;
    Toolbar toolbar;

    int v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_sua_loai_san_pham);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        actionBar();
        edtmaLoaiSP.setEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.them_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sua_Loaisanpham_Activity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xacNhan:
            {
                if (edtTenLoaiSP.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p T??n Loai S???n Ph???m!");
                    return true;
                }
                if (edtMieuta.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p mi??u t??? cho lo???i s???n ph???m!");
                    return true;
                }
                LoaiSPsua();
                return true;
            }
            case R.id.AB_huy:
            {
                Intent intent = new Intent(sua_Loaisanpham_Activity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setEvent() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sua_Loaisanpham_Activity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSuaLoaiSP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (edtTenLoaiSP.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p T??n Loai S???n Ph???m!");
                    return;
                }
                if (edtMieuta.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p mi??u t??? cho lo???i s???n ph???m!");
                    return;
                }
                LoaiSPsua();
            }
        });
    }

    private void LoaiSPsua() {
        loaisanpham lsp = getLoaiSP();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<loaisanpham> call = method.updateLoaiSanPham(lsp.getId(),lsp);
        call.enqueue(new Callback<loaisanpham>() {
            @Override
            public void onResponse(Call<loaisanpham> call, Response<loaisanpham> response) {
                thongbao("S???A TH??NH C??NG","B???n c?? X??c nh???n tho??t ch????ng tr??nh kh??ng?");
            }

            @Override
            public void onFailure(Call<loaisanpham> call, Throwable t) {
                thongbao("S???A TH???T B???I","B???n c?? X??c nh???n tho??t ch????ng tr??nh kh??ng?");
            }
        });
    }

    private void setControl() {
        btnSuaLoaiSP = findViewById(R.id.btnXacNhan);
        edtmaLoaiSP = findViewById(R.id.edtMaLoaiSP);
        edtTenLoaiSP = findViewById(R.id.edtTenLoaiSP);
        edtMieuta = findViewById(R.id.edtMieuTa);
        btnExit = findViewById(R.id.btnHuy);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id= bundle.getString("id");
        maLoaiSP = bundle.getString("maLoaiSP");
        tenLoaiSP = bundle.getString("tenLoaiSP");
        mieuta = bundle.getString("mieuta");
        v= bundle.getInt("v");
        edtmaLoaiSP.setText(maLoaiSP);
        edtTenLoaiSP.setText(tenLoaiSP);
        edtMieuta.setText(mieuta);
        toolbar = findViewById(R.id.toolbarfrmSuaLoaiSanPham);
    }
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // N??t Ok
        b.setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(sua_Loaisanpham_Activity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });
        //N??t Cancel
        b.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //T???o dialog
        AlertDialog al = b.create();
        //Hi???n th???
        al.show();
    }
    public void thongBaoLoi(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // N??t Ok
        b.setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //T???o dialog
        AlertDialog al = b.create();
        //Hi???n th???
        al.show();
    }
    private loaisanpham getLoaiSP() {
        loaisanpham lsp = new loaisanpham();
        lsp.setId(id);
        lsp.setMaLoaiSP(edtmaLoaiSP.getText().toString());
        lsp.setTenLoaiSP(edtTenLoaiSP.getText().toString());
        lsp.setMieuta(edtMieuta.getText().toString());
        lsp.setV(v);
        return lsp;
    }
}
