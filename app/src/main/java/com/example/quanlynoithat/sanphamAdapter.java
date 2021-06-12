package com.example.quanlynoithat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class sanphamAdapter extends ArrayAdapter<sanpham> {
    Context context;
    int layoutResourceId;
    ArrayList<sanpham> data = null;

    public sanphamAdapter(Context context, int layoutResourceId, ArrayList<sanpham> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    static class sanphamHolder
    {
        TextView edtMaSP,edtTenSP,edtGiaSP, edtLoaiSP;
        ImageView ivSP;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = vi.inflate(R.layout.listview_san_pham, null);
        }
        sanpham temp = data.get(position);
        if (temp != null) {
            ImageView iv = row.findViewById(R.id.listImage);
            TextView txtMaSP = row.findViewById(R.id.lvMaSP);
            TextView txtTenSP = row.findViewById(R.id.lvTenSP);
            TextView txtGiaSP = row.findViewById(R.id.lvGiaSP);
            TextView txtLoaiSP = row.findViewById(R.id.lvLoaiSP);
            txtMaSP.setText(temp.getMaSP());
            txtTenSP.setText(temp.getTenSP());
            txtGiaSP.setText(String.valueOf(temp.getGiaSP()));
            txtLoaiSP.setText(temp.getLoaiSP());
            if (iv != null) {
                Picasso.get().load(temp.getUrl()).into(iv);
            }

        }
        return row;
    }
}
