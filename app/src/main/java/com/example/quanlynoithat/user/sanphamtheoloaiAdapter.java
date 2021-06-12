package com.example.quanlynoithat.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class sanphamtheoloaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<sanpham> arraySPtheoloai ;

    public sanphamtheoloaiAdapter(Context context, ArrayList<sanpham> arraySPtheoloai) {
        this.context = context;
        this.arraySPtheoloai = arraySPtheoloai;
    }

    @Override
    public int getCount() {
        return arraySPtheoloai.size();
    }

    @Override
    public Object getItem(int position) {
        return arraySPtheoloai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class ViewHolder{
        public TextView tensanpham_theoloai,giasanpham_theoloai;
        public ImageView imageViewSP_theoloai;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongsanpham_theoloai,null);
            viewHolder.tensanpham_theoloai = view.findViewById(R.id.textviewTenSP_theoloai);
            viewHolder.giasanpham_theoloai = view.findViewById(R.id.textviewGiaSP_theoloai);
            viewHolder.imageViewSP_theoloai = view.findViewById(R.id.imageviewSP_theoloai);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        sanpham sp = (sanpham) getItem(position);
        viewHolder.tensanpham_theoloai.setText(sp.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.giasanpham_theoloai.setText("Giá: "+ decimalFormat.format(sp.getGiaSP())+" Đ");
        Picasso.get().load(sp.getUrl()).into(viewHolder.imageViewSP_theoloai);
        return view;
    }
}
