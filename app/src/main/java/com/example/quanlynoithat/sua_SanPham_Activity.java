package com.example.quanlynoithat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.widget.Toolbar;
public class sua_SanPham_Activity extends AppCompatActivity {
    EditText edtmaSP, edtTenSP,edtgiaSP;
    Spinner spnLoaiSP;
    ImageView ivSP;
    Button btnSuaSP,btnExit;
    String maLoaiSP;
    ArrayList<String> allLoaiSP = new ArrayList<>();

    String tempLoaiSP,url,maSP,tenSP,giaSP,id;
    int v;
    ArrayAdapter adapter=null;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_sua_san_pham);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setControl();
        setEvent();
        actionBar();
        edtmaSP.setEnabled(false);
    }

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
                Intent intent = new Intent(sua_SanPham_Activity.this, sanPham_activity.class);
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
                if (edtTenSP.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Tên Sản Phẩm!");
                    return true;
                }
                if (edtgiaSP.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Giá Sản Phẩm!");
                    return true;
                }
                SPsua();
                return true;
            }
            case R.id.AB_huy:
            {
                Intent intent = new Intent(sua_SanPham_Activity.this, sanPham_activity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allLoaiSP);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnLoaiSP.setAdapter(adapter);
        loadLoaiSP();
        spnLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempLoaiSP = allLoaiSP.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sua_SanPham_Activity.this, sanPham_activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSuaSP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (edtTenSP.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Tên Sản Phẩm!");
                    return;
                }
                if (edtgiaSP.getText().length() == 0)
                {
                    thongBaoLoi("Lỗi","Vui lòng nhập Giá Sản Phẩm!");
                    return;
                }
                SPsua();
            }
        });

        ivSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallarie();
            }
        });
    }

    private void SPsua() {
        sanpham sp = getSP();
        //đẩy ảnh lên ừ stoega chỉ

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://quanlynoithat-df330.appspot.com/");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(sp.getMaSP()+".jpg");

        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("images/"+sp.getMaSP()+".jpg");

        // While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        ivSP.setDrawingCacheEnabled(true);
        ivSP.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivSP.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return mountainsRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    //lấy url về chịu code có sản cua firebase
                    url=downloadUri.toString();
                    uploadData();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    public void uploadData()
    {
        sanpham sp = getSP();
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<sanpham> call = method.updateSanPham(sp.getId(),sp);
        call.enqueue(new Callback<sanpham>() {
            @Override
            public void onResponse(Call<sanpham> call, Response<sanpham> response) {
                Log.v("phan hoi",response.body().toString());
                thongbao("SỬA THÀNH CÔNG","Bạn có Xác nhận thoát chương trình không?");
            }

            @Override
            public void onFailure(Call<sanpham> call, Throwable t) {
                thongbao("SỬA THẤT BẠI","Bạn có Xác nhận thoát chương trình không?");
                Log.v("phan hoi","fail");
            }
        });


    }
    private void loadLoaiSP() {
        method method= RitrofitClient.getRetrofit().create(method.class);
        Call<ArrayList<loaisanpham>> call = method.getLoaiSanPham();
        call.enqueue(new Callback<ArrayList<loaisanpham>>() {
            @Override
            public void onResponse(Call<ArrayList<loaisanpham>> call, Response<ArrayList<loaisanpham>> response) {
                allLoaiSP.clear();
                for(int i=0;i<response.body().size();i++)
                {
                    allLoaiSP.add(response.body().get(i).getTenLoaiSP());
                }
                adapter.notifyDataSetChanged();
                // lúc trước tìm ở hàm trên, giờ đặt ở đây
                for(int i=0;i<allLoaiSP.size();i++)
                {
                    if(allLoaiSP.get(i).equals(maLoaiSP))
                    {
                        spnLoaiSP.setSelection(i);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<loaisanpham>> call, Throwable t) {
                Log.v("phan hoi",t.toString());
            }
        });
    }

    private void setControl() {
        btnSuaSP = findViewById(R.id.btnXacNhan);
        edtmaSP = findViewById(R.id.edtMaSP);
        edtTenSP = findViewById(R.id.edtTenSP);
        edtgiaSP = findViewById(R.id.edtGiaSP);
        btnExit = findViewById(R.id.btnHuy);
        ivSP = findViewById(R.id.imgAdd_insert);
        //getAllLoaiSP();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id= bundle.getString("id");
        maSP = bundle.getString("maSP");
        tenSP = bundle.getString("tenSP");
        giaSP = String.valueOf(bundle.getFloat("giaSP"));
        maLoaiSP = bundle.getString("loaiSP");
        url = bundle.getString("url");
        v= bundle.getInt("v");
        edtmaSP.setText(maSP);
        edtTenSP.setText(tenSP);
        edtgiaSP.setText(giaSP);
        Picasso.get().load(url).into(ivSP);
        spnLoaiSP = findViewById(R.id.spnLoaiSP);
        toolbar = findViewById(R.id.toolbarfrmSuaSanPham);
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
    public void thongbao(String title,String mes)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(mes);
        // Nút Ok
        b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(sua_SanPham_Activity.this, sanPham_activity.class);
                startActivity(intent);
                finish();
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
    private sanpham getSP() {
        sanpham sp = new sanpham();
        sp.setId(id);
        sp.setMaSP(edtmaSP.getText().toString());
        sp.setTenSP(edtTenSP.getText().toString());
        sp.setGiaSP(Float.valueOf(edtgiaSP.getText().toString()));
        sp.setLoaiSP(tempLoaiSP);
        sp.setUrl(url);
        sp.setV(v);
        return sp;
    }

    public void openGallarie()
    {
        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                ivSP.setImageBitmap(decodeStream);
            }
            catch (Exception ex)
            {
                Log.e("ex", ex.getMessage());
            }
        }
    }
}
