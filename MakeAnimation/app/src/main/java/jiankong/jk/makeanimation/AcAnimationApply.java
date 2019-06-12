package jiankong.jk.makeanimation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jiankong.jk.makeupanimation.AnimationChartView;
import jiankong.jk.makeupanimation.Animators;
import jiankong.jk.makeupanimation.setAniamtionChartClickListener;

public class AcAnimationApply extends Activity {
    private AnimationChartView acv;
    Animators animators=new Animators();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acanimation);
        init();
    }
    private void init(){
        acv =  findViewById(R.id.zzt1);

        final List<HashMap<String,String>> list = new ArrayList<>();
        HashMap<String,String> map5 = new HashMap<>();
        map5.put("name","1");
        map5.put("value", "800");
        map5.put("unit", "X");
        list.add(map5);
        HashMap<String,String> map6 = new HashMap<>();
        map6.put("name","2");
        map6.put("value", "1500");
        map6.put("unit", "X");
        list.add(map6);
        HashMap<String,String> map7 = new HashMap<>();
        map7.put("name","3");
        map7.put("value", "2000");
        map7.put("unit", "X");
        list.add(map7);
        HashMap<String,String> map8 = new HashMap<>();
        map8.put("name","4");
        map8.put("value", "2500");
        map8.put("unit", "X");
        list.add(map8);
        animators.setAnimationChartViewDatas(acv,list,"test",7000);


    }
}
