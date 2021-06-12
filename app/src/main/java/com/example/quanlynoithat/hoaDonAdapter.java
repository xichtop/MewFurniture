package com.example.quanlynoithat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class hoaDonAdapter extends ArrayAdapter<hoaDon> {
    Context context;
    int layoutResourceId;
    ArrayList<hoaDon> data = null;

    public hoaDonAdapter(Context context, int layoutResourceId, ArrayList<hoaDon> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    static class hoaDonHolder
    {
        TextView edtMaHD,edtMaKh,edtNgayHD,edtTinhTrang;
    }
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        hoaDonHolder holder = null;
        if(row!=null)
        {
            holder = (hoaDonHolder) row.getTag();
        }
        else
        {
            holder = new hoaDonHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.listview_hoa_don,parent,false);
            holder.edtMaHD = row.findViewById(R.id.lvMaHD);
            holder.edtMaKh = row.findViewById(R.id.lvMaKH);
            holder.edtNgayHD = row.findViewById(R.id.lvNgayHD);
            holder.edtTinhTrang = row.findViewById(R.id.lvTrangThai);
            row.setTag(holder);
        }
        final hoaDon nt = data.get(position);
        holder.edtMaHD.setText(nt.getMaHD());
        holder.edtMaKh.setText(nt.getSdt());
        holder.edtNgayHD.setText(nt.getNgayThem());
        holder.edtTinhTrang.setText(nt.getTinhTrang());
        return row;
    }
}
