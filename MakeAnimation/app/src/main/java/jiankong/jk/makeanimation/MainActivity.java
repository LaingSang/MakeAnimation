package jiankong.jk.makeanimation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jiankong.jk.makeupanimation.Animators;
import jiankong.jk.makeupanimation.ChartBean;
import jiankong.jk.makeupanimation.HorChartView;

public class MainActivity extends Activity {

    private ImageView imgdhceshi;
    private ImageView imgsrceshi;
    private ImageView imgsrceshiD;
    private HorChartView hcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        imgdhceshi=findViewById(R.id.dhceshi);
        hcv=findViewById(R.id.testcharview);
        imgsrceshi=findViewById(R.id.srceshi);
        imgsrceshiD=findViewById(R.id.dhceshid);
        Animators animators=new Animators();
        animators.ATanimaotion(imgdhceshi,false,800,0,500);
        animators.LoopRotaAnimaotion(imgsrceshi,0,-360);
        animators.ExplosionAniamtion(this,imgsrceshiD,true);
        List<ChartBean> chartBeans=new ArrayList<>();
        chartBeans.add(new ChartBean(10,"x1"));
        chartBeans.add(new ChartBean(20,"x2"));
        chartBeans.add(new ChartBean(30,"x3"));
        animators.HorChartView(hcv,chartBeans);
    }
}
