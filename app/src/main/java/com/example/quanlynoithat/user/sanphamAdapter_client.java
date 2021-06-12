package com.example.quanlynoithat.user;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.RitrofitClient;
import com.example.quanlynoithat.loaisanpham;
import com.example.quanlynoithat.method;
import com.example.quanlynoithat.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sanphamAdapter_client extends RecyclerView.Adapter<sanphamAdapter_client.ItemHolder> {
    Context context;
    ArrayList<sanpham> arraySanPham;
    ArrayList<sanpham> dataTemp = new ArrayList<>();
    public sanphamAdapter_client(Context context, ArrayList<sanpham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        sanpham sanpham = arraySanPham.get(position);
        holder.tenSp.setText(sanpham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giaSP.setText("Giá: "+ decimalFormat.format(sanpham.getGiaSP())+" Đ");
        Picasso.get().load(sanpham.getUrl()).into(holder.hinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraySanPham.size();
    }

    public  class ItemHolder extends  RecyclerView.ViewHolder{
        public ImageView hinhsanpham;
        public TextView tenSp,giaSP;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            hinhsanpham = itemView.findViewById(R.id.imageviewSanPham);
            tenSp = itemView.findViewById(R.id.textviewTenSP);
            giaSP = itemView.findViewById(R.id.textviewGiaSP);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,chitietsanpham.class);
                    intent.putExtra("thongtinsp",arraySanPham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("aaa: ",arraySanPham.get(getPosition()).getTenSP());
                    context.startActivity(intent);
                }
            });
        }
    }
}
