package com.example.quanlynoithat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    ArrayList<sanpham> data = null;

    public CustomSpinnerAdapter(Context applicationContext, ArrayList<sanpham> data) {
        this.context = applicationContext;
        this.data = data;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_cthd, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageSpinner);
        TextView names = (TextView) view.findViewById(R.id.tvSpinner);
        final sanpham s = data.get(i);
        if (icon != null) {
            Picasso.get().load(s.getUrl()).into(icon);
        }
        names.setText(s.getMaSP());
        return view;
    }
}
