package com.example.quanlynoithat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class cthdAdapter extends ArrayAdapter<cthd> {
    Context context;
    int layoutResourceId;
    ArrayList<cthd> data = null;

    public cthdAdapter(Context context, int layoutResourceId, ArrayList<cthd> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    static class cthdHolder
    {
        TextView edtMaHD,edtMaSP,edtSoLuong;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        cthdAdapter.cthdHolder holder = null;
        if(row!=null)
        {
            holder = (cthdHolder) row.getTag();
        }
        else
        {
            holder = new cthdHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.listview_chi_tiet_hoa_don,parent,false);
            holder.edtMaHD = row.findViewById(R.id.lvMHD);
            holder.edtMaSP = row.findViewById(R.id.lvMSP);
            holder.edtSoLuong = row.findViewById(R.id.lvSL);
            row.setTag(holder);
        }
        final cthd c = data.get(position);
        holder.edtMaHD.setText(c.getMaHD());
        holder.edtMaSP.setText(c.getMaSP());
        holder.edtSoLuong.setText(String.valueOf(c.getSoLuong()));
        return row;
    }
}
