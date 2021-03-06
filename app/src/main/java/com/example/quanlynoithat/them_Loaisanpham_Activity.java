package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.widget.Toolbar;

public class them_Loaisanpham_Activity extends AppCompatActivity {
    EditText edtmaLoaiSP, edtTenLoaiSP,edtMieutaLoaiSP;
    Button btnXacNhanThemLoaiSP,btnTroVe;
    Boolean kiemTraTrung=true;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_them_loai_san_pham);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        actionBar();
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
                Intent intent = new Intent(them_Loaisanpham_Activity.this, loaiSanPham_activity.class);
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
                if (edtmaLoaiSP.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p M?? lo???i s???n ph???m");
                    return true;
                }

                if (edtTenLoaiSP.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p T??n Loai S???n Ph???m!");
                    return true;
                }
                if (edtMieutaLoaiSP.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p mi??u t??? cho lo???i s???n ph???m!");
                    return true;
                }
                kiemTraTrung();
                if(kiemTraTrung)
                {
                    thongBaoLoi("L???i","M?? lo???i s???n ph???m ???? t???n t???i");
                    return true;
                }
                LoaiSPNhap();
                return true;
            }
            case R.id.AB_huy:
            {
                Intent intent = new Intent(them_Loaisanpham_Activity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    private void setEvent()
    {
        btnXacNhanThemLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (edtmaLoaiSP.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p M?? lo???i s???n ph???m");
                    return;
                }
                if (edtTenLoaiSP.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p T??n Loai S???n Ph???m!");
                    return;
                }
                if (edtMieutaLoaiSP.getText().length() == 0)
                {
                    thongBaoLoi("L???i","Vui l??ng nh???p mi??u t??? cho lo???i s???n ph???m!");
                    return;
                }
                kiemTraTrung();

            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(them_Loaisanpham_Activity.this, loaiSanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControl() {
        btnXacNhanThemLoaiSP = findViewById(R.id.btnXacNhan);
        edtmaLoaiSP = findViewById(R.id.edtMaLoaiSP);
        edtTenLoaiSP = findViewById(R.id.edtTenLoaiSP);
        edtMieutaLoaiSP = findViewById(R.id.edtMieuTa);
        btnTroVe = findViewById(R.id.btnHuy);
        toolbar = findViewById(R.id.toolbarfrmThemLoaiSanPham);
    }
    private void kiemTraTrung() {
        loaisanpham lsp = getLoaiSP();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<loaisanpham>> call = method.checkLoaiSanPham(lsp.getMaLoaiSP());
        call.enqueue(new Callback<ArrayList<loaisanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<loaisanpham>> call, Response<ArrayList<loaisanpham>> response) {
                Log.v("phan hoi",response.body().toString());
                if(response.body().size()>0)
                {
                    thongBaoLoi("L???i","M?? lo???i s???n ph???m ???? t???n t???i");
                    return;
                }
                else
                    LoaiSPNhap();
            }

            @Override
            public void onFailure(Call<ArrayList<loaisanpham>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }
    private void LoaiSPNhap() {
        loaisanpham lsp = getLoaiSP();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<loaisanpham> call = method.saveLoaiSanPham(lsp);
        call.enqueue(new Callback<loaisanpham>() {
            @Override
            public void onResponse(Call<loaisanpham> call, Response<loaisanpham> response) {
                Log.v("phan hoi",response.body().toString());
                thongbao("TH??M TH??NH C??NG","B???n c?? X??c nh???n tho??t ch????ng tr??nh kh??ng?");
            }

            @Override
            public void onFailure(Call<loaisanpham> call, Throwable t) {
                thongbao("TH??M TH???T B???I","B???n c?? X??c nh???n tho??t ch????ng tr??nh kh??ng?");
                Log.v("phan hoi","fail");
            }
        });
    }

    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // N??t Ok
        b.setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(them_Loaisanpham_Activity.this, loaiSanPham_activity.class);
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
        lsp.setMaLoaiSP(edtmaLoaiSP.getText().toString());
        lsp.setTenLoaiSP(edtTenLoaiSP.getText().toString());
        lsp.setMieuta(edtMieutaLoaiSP.getText().toString());
        return lsp;
    }
}
