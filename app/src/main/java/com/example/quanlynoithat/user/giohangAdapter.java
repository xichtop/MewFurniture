package com.example.quanlynoithat.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlynoithat.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class giohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> giohangArrayList;

    public giohangAdapter(Context context, ArrayList<Giohang> giohangArrayList) {
        this.context = context;
        this.giohangArrayList = giohangArrayList;
    }

    @Override
    public int getCount() {
        return giohangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return giohangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class  ViewHolder{
        public TextView tenGiohang,giaGiohang;
        public ImageView imageViewGiohang;
        public Button btnTru,btnCong,btnValue;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.tenGiohang = view.findViewById(R.id.textviewTenGiohang);
            viewHolder.giaGiohang = view.findViewById(R.id.textviewGiagiohang);
            viewHolder.imageViewGiohang = view.findViewById(R.id.imageViewGiohang);
            viewHolder.btnTru = view.findViewById(R.id.buttonTru);
            viewHolder.btnValue = view.findViewById(R.id.buttonValue);
            viewHolder.btnCong = view.findViewById(R.id.buttonCong);
            view.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.tenGiohang.setText(giohang.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.giaGiohang.setText(decimalFormat.format(giohang.getGiaSp())+ " Đ");
        Picasso.get().load(giohang.getAnhSp()).into(viewHolder.imageViewGiohang);
        viewHolder.btnValue.setText(giohang.getSl() + "");
        int sl = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if(sl<=1){
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.btnTru.setVisibility(View.VISIBLE);
            viewHolder.btnCong.setVisibility(View.VISIBLE);
        }
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slnew = Integer.parseInt(finalViewHolder.btnValue.getText().toString())+1;
                int slnow = trangchu.mangGiohang.get(position).getSl();
                float gianow = trangchu.mangGiohang.get(position).getGiaSp();
                trangchu.mangGiohang.get(position).setSl(slnew);
                float gianew = (gianow * slnew) / slnow;
                trangchu.mangGiohang.get(position).setGiaSp(gianew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.giaGiohang.setText(decimalFormat.format(gianew)+ " Đ");
                GioHangActivity.getDulieuTotal();

                finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                finalViewHolder.btnValue.setText(String.valueOf(slnew));
            }
        });
        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slnew = Integer.parseInt(finalViewHolder.btnValue.getText().toString())-1;
                int slnow = trangchu.mangGiohang.get(position).getSl();
                float gianow = trangchu.mangGiohang.get(position).getGiaSp();
                trangchu.mangGiohang.get(position).setSl(slnew);
                float gianew = (gianow * slnew) / slnow;
                trangchu.mangGiohang.get(position).setGiaSp(gianew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.giaGiohang.setText(decimalFormat.format(gianew)+ " Đ");
                GioHangActivity.getDulieuTotal();
                if(slnew<2)
                {
                    finalViewHolder.btnTru.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slnew));
                }
                else{
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slnew));
                }
            }
        });
        return view;
    }
}
