package com.example.quanlynoithat.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlynoithat.R;
import com.example.quanlynoithat.hoaDon;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class hoaDonKhachHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<hoaDon> arrayHD;

    public hoaDonKhachHangAdapter(Context context, ArrayList<hoaDon> arrayHD) {
        this.context = context;
        this.arrayHD = arrayHD;
    }

    @Override
    public int getCount() {
        return arrayHD.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayHD.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class ViewHolder{
        public TextView maHD, ngayHD, trangThatHD;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        hoaDonKhachHangAdapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new hoaDonKhachHangAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongsanpham_theoloai,null);
            viewHolder.maHD = view.findViewById(R.id.textviewTenSP_theoloai);
            viewHolder.ngayHD = view.findViewById(R.id.textviewGiaSP_theoloai);
            viewHolder.trangThatHD = view.findViewById(R.id.textviewmotaSp_theoloai);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (hoaDonKhachHangAdapter.ViewHolder) view.getTag();
        }
        hoaDon hd = (hoaDon) getItem(position);
        viewHolder.maHD.setText(hd.getMaHD());
        viewHolder.ngayHD.setText("Ngày: "+ hd.getNgayThem());
        viewHolder.trangThatHD.setText("Trạng thái: " + hd.getTinhTrang());
        return view;
    }
}
