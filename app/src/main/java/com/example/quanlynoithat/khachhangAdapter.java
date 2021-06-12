package com.example.quanlynoithat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class khachhangAdapter extends ArrayAdapter<khachhang> {
    Context context;
    int layoutResourceId;
    ArrayList<khachhang> data = null;

    public khachhangAdapter(Context context, int layoutResourceId, ArrayList<khachhang> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    static class khachhangHolder
    {
        TextView edtSDTkh, edtHoTenKh, edtDiachiKh, edtEmail, edtPassword;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        khachhangHolder holder = null;
        if(row!=null)
        {
            holder = (khachhangHolder) row.getTag();
        }
        else
        {
            holder = new khachhangHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.listview_khach_hang,parent,false);
            holder.edtSDTkh = row.findViewById(R.id.listSDTKH);
            holder.edtHoTenKh = row.findViewById(R.id.listHoTenKh);
            holder.edtDiachiKh = row.findViewById(R.id.listDiachiKh);
            holder.edtEmail = row.findViewById(R.id.listEmail);
            holder.edtPassword = row.findViewById(R.id.listMK);
            row.setTag(holder);
        }
        final khachhang nt = data.get(position);
        holder.edtSDTkh.setText(nt.getSdtKh());
        holder.edtHoTenKh.setText(nt.getHotenKh());
        holder.edtDiachiKh.setText(nt.getDiachiKh());
        holder.edtEmail.setText(nt.getEmail());
        holder.edtPassword.setText(nt.getPassword());
        return row;
    }
}
