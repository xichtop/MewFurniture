package com.example.quanlynoithat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class doanhThuAdapter extends ArrayAdapter {
    Context context;
    int layoutResourceId;
    ArrayList<doanhThu> data = null;

    public doanhThuAdapter(Context context, int layoutResourceId, ArrayList<doanhThu> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    static class doanhThuHolder {
        TextView edtMa, edtTen,edtSoLuong, edtDoanhThu, edtThoiGian;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        doanhThuHolder holder = null;
        if (row != null) {
            holder = (doanhThuHolder) row.getTag();
        } else {
            holder = new doanhThuHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.listview_doanh_thu, parent, false);
            holder.edtMa = row.findViewById(R.id.lvDT_ma);
            holder.edtTen = row.findViewById(R.id.lvDT_ten);
            holder.edtSoLuong = row.findViewById(R.id.lvDT_SoLuong);
            holder.edtDoanhThu = row.findViewById(R.id.lvDT_doanhThu);
            holder.edtThoiGian = row.findViewById(R.id.lvDT_thoiGian);
            row.setTag(holder);
        }
        final doanhThu nt = data.get(position);
        holder.edtMa.setText(nt.getMaNT());
        holder.edtTen.setText(nt.getTenNT());
        holder.edtSoLuong.setText(String.valueOf(nt.getSoLuong()));
        holder.edtDoanhThu.setText(String.valueOf(nt.getDoanhThu()));
        holder.edtThoiGian.setText(nt.getThoiGian());
        return row;
    }
}