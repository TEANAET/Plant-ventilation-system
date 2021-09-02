package com.example.keshe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import mysqllink.DBUtils;
import mysqllink.WorkDb;
import mysqllink.dht1;

public class ShowActivity extends BaseActivity {
    private CombinedChart combinedChart;
    private boolean portrait;
    private List<dht1> data;
    private float dht_s[],dht_h[];
    private Button button;
    private float aa;
    protected String[] suppliers = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_show);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        button=findViewById(R.id.show_bt);
        combinedChart = (CombinedChart) findViewById(R.id.chart);
        //调用线程
        workDb = new WorkDb(this.handler);
        //读数据
        workDb.read();

    }

    //初始化
    private void init(){

        setlister();

        initcombind();
        setXAxis();
        setYAxis();

        List<dht1> retList = new ArrayList<>();
        dht_s = new float[data.size()];
        dht_h = new float[data.size()];
        for(int i=0;i<suppliers.length;i++){
            dht_s[i]= data.get(i).getTem1();
            dht_h[i]= Float.parseFloat(data.get(i).getHum1());
        }
        setData(dht_s,dht_h);
    }

    //初始化组合图
    private void initcombind(){
        combinedChart.setDrawBorders(true); // 显示边界
        combinedChart.getDescription().setEnabled(false);  // 显示备注信息6*/9
        combinedChart.setPinchZoom(true); // 比例缩放
        combinedChart.animateY(1500);
    }


    //设置X轴
    private void setXAxis(){
        //获取在x轴上的操作类
        XAxis xAxis = combinedChart.getXAxis();
        //将此设置为true以启用绘制此轴的网格线
        xAxis.setDrawGridLines(false);
        /*解决左右两端柱形图只显示一半的情况 只有使用CombinedChart时会出现，如果单独使用BarChart不会有这个问题*/
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(suppliers.length - 0.5f);
        // 设置X轴标签数量
        xAxis.setLabelCount(suppliers.length);
        // 设置X轴标签位置，BOTTOM在底部显示，TOP在顶部显示
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        /* 转换要显示的标签文本，value值默认是int从0开始  eg：0,1,2,3
        使用由图表计算的格式化程序，再显示在图中*/
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return suppliers[(int) value];
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
    }


    //设置数据
    private  void setData(float a[],float b[]){
        /**
         * 初始化柱形图的数据
         * 此处用suppliers的数量做循环，因为总共所需要的数据源数量应该和标签个数一致
         * 其中BarEntry是柱形图数据源的实体类，包装xy坐标数据
         */
        /******************BarData start********************/
        List<BarEntry> barEntries = new ArrayList<>();
        String[] suppliers = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
        for (int i = 0; i < suppliers.length; i++) {
            barEntries.add(new BarEntry(i, getRandom(0, 0)+b[i]));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "湿度");  // 新建一组柱形图，"温度"为本组柱形图的Label
        barDataSet.setColor(Color.parseColor("#0288d1")); // 设置柱形图颜色
        barDataSet.setValueTextColor(Color.parseColor("#0288d1")); //  设置柱形图顶部文本颜色
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);// 添加一组柱形图，如果有多组柱形图数据，则可以多次addDataSet来设置
        /******************BarData end********************/

        /**
         * 初始化折线图数据
         * 说明同上
         */
        /******************LineData start********************/
        List<Entry> lineEntries = new ArrayList<>();
        for (int i = 0; i < suppliers.length; i++) {
            lineEntries.add(new Entry(i,getRandom(0,0)+a[i]));
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "温度");
        lineDataSet.setColor(Color.parseColor("#b71c1c"));
        lineDataSet.setCircleColor(Color.parseColor("#b71c1c"));
        lineDataSet.setValueTextColor(Color.parseColor("#f44336"));
        lineDataSet.setLineWidth(3f);
        lineDataSet.setHighlightEnabled(true);
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        /******************LineData end********************/
        CombinedData combinedData = new CombinedData(); // 创建组合图的数据源
        combinedData.setData(barData);  // 添加柱形图数据源
        combinedData.setData(lineData); // 添加折线图数据源
        combinedChart.setData(combinedData); // 为组合图设置数据源
    }

    //设置Y轴
    private void setYAxis(){
        // 返回左y轴对象，获取左边Y轴操作类
        YAxis axisLeft = combinedChart.getAxisLeft();
        axisLeft.setAxisMinimum(0); // 设置此轴的最小值
        axisLeft.setGranularity(5); // 设置Label间隔
        axisLeft.setLabelCount(15);// 设置y轴上标签数量
        // 在左边Y轴标签文本后加上%号
        axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "%";
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        YAxis axisRight = combinedChart.getAxisRight(); // 获取右边Y轴操作类
        axisRight.setDrawGridLines(false); // 不绘制背景线，上面左边Y轴并没有设置此属性，因为不设置默认是显示的
        axisRight.setGranularity(2); // 设置Label间隔
        axisRight.setAxisMinimum(0); // 设置最小值
        axisRight.setLabelCount(20); // 设置标签个数
        // 在右边Y轴标签文本后加上°C号
        axisRight.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "°C";
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
    }
    private WorkDb workDb;

    //设置按钮监听器
    private void setlister(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workDb != null) {
                    workDb.read();
                }

              /*  new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.performClick();
                    }
                },300000);*/
              //隔10秒自动点击一次
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.performClick();
                    }
                },10000);
            }
        });
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {

            data = (List<dht1>) msg.obj;
            init();
        }
    };
    /**
     * onNavButtonsTapped(): 点击导航栏上的标签时触发。
     *
     * @param v 点击的按钮对象，用 v.getId() 获取其资源 ID。
     */
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavMessage:
                open(ControlActivity.class);
                break; // case R.id.btnNavMessage

            case R.id.btnNavHome:
                open(HomeActivity.class);
                break; // case R.id.btnNavSettings
        } // switch (v.getId())
    } // onNavButtonsTapped()*/

    /**
     * onKeyDown(): 按下回退键时触发。
     * 弹出对话框询问是否退出程序。
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        } // if (keyCode == KeyEvent.KEYCODE_BACK)
        else {
            return super.onKeyDown(keyCode, event);
        } // else
    } // onKeyDown()
}
