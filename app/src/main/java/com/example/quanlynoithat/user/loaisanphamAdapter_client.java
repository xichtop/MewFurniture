package com.example.quanlynoithat.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.loaisanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class loaisanphamAdapter_client extends BaseAdapter {

    ArrayList<loaisanpham> arrayListLoaiSP;
    Context context;

    public loaisanphamAdapter_client(ArrayList<loaisanpham> arrayListLoaiSP, Context context) {
        this.arrayListLoaiSP = arrayListLoaiSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaiSP.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListLoaiSP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class  ViewHolder{
        TextView txtTenLoaiSP;
        ImageView imgLoaiSP;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txtTenLoaiSP = view.findViewById(R.id.textviewLSP);
            viewHolder.imgLoaiSP = view.findViewById(R.id.imageviewLSP);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        loaisanpham lsp = (loaisanpham) getItem(position);
        viewHolder.txtTenLoaiSP.setText(lsp.getTenLoaiSP());
        if(position>0){
            if (position == arrayListLoaiSP.size()-1)
            {
                viewHolder.imgLoaiSP.setImageResource(R.drawable.hoadonicon);
            }
            else
            {
                Picasso.get().load("https://bit.ly/3p5VSj1").into(viewHolder.imgLoaiSP);
            }
        }
        else{
            Picasso.get().load("https://bit.ly/3vCx052").into(viewHolder.imgLoaiSP);
        }
        return view;
    }
}
