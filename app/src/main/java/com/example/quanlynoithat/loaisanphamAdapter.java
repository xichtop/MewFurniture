package com.example.quanlynoithat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class loaisanphamAdapter extends ArrayAdapter<loaisanpham> {
    Context context;
    int layoutResourceId;
    ArrayList<loaisanpham> data = null;
    public loaisanphamAdapter(Context context, int layoutResourceId, ArrayList<loaisanpham> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    static class loaisanphamHolder
    {
        TextView edtMaLoaiSP,edtTenLoaiSP,edtMieutaSP;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        loaisanphamAdapter.loaisanphamHolder holder = null;
        if(row!=null)
        {
            holder = (loaisanphamAdapter.loaisanphamHolder) row.getTag();
        }
        else
        {
            holder = new loaisanphamAdapter.loaisanphamHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.listview_loai_san_pham,parent,false);
            holder.edtMaLoaiSP = row.findViewById(R.id.lvMaLoaiSP);
            holder.edtTenLoaiSP = row.findViewById(R.id.lvTenLoaiSP);
            holder.edtMieutaSP = row.findViewById(R.id.lvMieuTa);
            row.setTag(holder);
        }
        final loaisanpham lsp = data.get(position);
        holder.edtMaLoaiSP.setText(lsp.getMaLoaiSP());
        holder.edtTenLoaiSP.setText(lsp.getTenLoaiSP());
        holder.edtMieutaSP.setText(lsp.getMieuta());
        return row;
    }
}
