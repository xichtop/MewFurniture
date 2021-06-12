package com.example.quanlynoithat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Random;

public class bieu_do_Activity extends AppCompatActivity implements OnChartValueSelectedListener {
    private PieChart mChart;
    ArrayList<String> tenSP = new ArrayList<>();
    ArrayList<String> doanhThu = new ArrayList<>();
    ArrayList<Float> yData = new ArrayList<>();
    ArrayList<String> xData = new ArrayList<>();
    String thoiGian="",thang="",nam="";
    Button btnTroVe;
    TextView value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_bieu_do);
        setControl();
        setEvent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.them_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.AB_xacNhan:
            {
                return true;
            }
            case R.id.AB_troVe:
            {
                Intent intent = new Intent(bieu_do_Activity.this, ketQua_doanhThu_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("thang",thang);
                bundle.putString("nam",nam);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        mChart = (PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Doanh Thu");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);

        btnTroVe = findViewById(R.id.NT_btnTroVe);
        value = findViewById(R.id.value);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            thang = bundle.getString("thang");
            nam = bundle.getString("nam");
            thoiGian += thang;
            thoiGian += "/";
            thoiGian += nam;
            tenSP = bundle.getStringArrayList("tenSP");
            doanhThu = bundle.getStringArrayList("doanhThu");

        }
    }

    private void setEvent() {
        addDataSet(mChart);
        mChart.setOnChartValueSelectedListener(this);

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bieu_do_Activity.this, ketQua_doanhThu_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("thang",thang);
                bundle.putString("nam",nam);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        value.setText(xData.get(Math.round(h.getX())) + " Doanh thu:  " + yData.get(Math.round(h.getX())));
    }

    @Override
    public void onNothingSelected() {

    }
    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();


        for(int i=0;i<tenSP.size();i++)
        {
            xData.add(tenSP.get(i));
        }
        for(int i=0;i<doanhThu.size();i++)
        {
            yData.add(Float.parseFloat(doanhThu.get(i)));
        }

        for (int i = 0; i < yData.size();i++){
            yEntrys.add(new PieEntry(yData.get(i),i));
        }
        for (int i = 0; i < xData.size();i++){
            xEntrys.add(xData.get(i));
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Doanh Thu Sản Phẩm");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(14);

        ArrayList<Integer> colors=new ArrayList<>();

        for(int i=0;i<yData.size();i++)
        {
            Boolean check =true;
            int value=0;
            if(colors.size()==0)
            {
                Random rnd = new Random();
                value = rnd.nextInt();
            }
            do {
                Random rnd = new Random();
                value = rnd.nextInt();
                for(int j=0;j<colors.size();j++)
                {
                    if(colors.get(j) == Color.rgb(value,value,value))
                        check = true;
                }
                check = false;
            }while(check);

            colors.add(Color.rgb(value,value,value));
        }

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

}
