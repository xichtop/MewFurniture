package com.example.quanlynoithat.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlynoithat.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class cthdKH_Adapter extends BaseAdapter {
    Context context;
    ArrayList<cthd_SP> arrayCTHD ;

    public cthdKH_Adapter(Context context, ArrayList<cthd_SP> arrayCTHD) {
        this.context = context;
        this.arrayCTHD = arrayCTHD;
    }

    @Override
    public int getCount() {
        return arrayCTHD.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCTHD.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class ViewHolder{
        public TextView tensanpham, giasanpham, soLuongSP;
        public ImageView imageViewSP;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        cthdKH_Adapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new cthdKH_Adapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongsanpham_theoloai,null);
            viewHolder.tensanpham = view.findViewById(R.id.textviewTenSP_theoloai);
            viewHolder.giasanpham = view.findViewById(R.id.textviewGiaSP_theoloai);
            viewHolder.soLuongSP = view.findViewById(R.id.textviewmotaSp_theoloai);
            viewHolder.imageViewSP = view.findViewById(R.id.imageviewSP_theoloai);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (cthdKH_Adapter.ViewHolder) view.getTag();
        }
        cthd_SP cs = (cthd_SP) getItem(position);
        viewHolder.tensanpham.setText(cs.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.giasanpham.setText("Giá: "+ decimalFormat.format(cs.getGiaSP())+" VNĐ");
        viewHolder.soLuongSP.setText("Số lượng: "+ decimalFormat.format(cs.getSoLuongSP()));
        Picasso.get().load(cs.getUrl()).into(viewHolder.imageViewSP);
        return view;
    }
}
