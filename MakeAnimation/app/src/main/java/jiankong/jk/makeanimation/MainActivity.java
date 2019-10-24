package jiankong.jk.makeanimation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jiankong.jk.makeupanimation.Animators;
import jiankong.jk.makeupanimation.ChartBean;
import jiankong.jk.makeupanimation.GradualCircularView;
import jiankong.jk.makeupanimation.HorChartView;
import jiankong.jk.makeupanimation.HttpTools;
import jiankong.jk.makeupanimation.WsConn;
import jiankong.jk.makeupanimation.ws.WSManager;
import jiankong.jk.makeupanimation.ws.WsStatusListener;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.ByteString;

public class MainActivity extends Activity {

    private ImageView imgdhceshi;
    private ImageView imgsrceshi;
    private ImageView imgsrceshiD;
    private HorChartView hcv;
    private WSManager ws;
    private WsStatusListener wsStatusListener=new WsStatusListener() {
        @Override
        public void onOpen(Response response) {
            super.onOpen(response);
        }

        @Override
        public void onMessage(String text) {
            super.onMessage(text);
        }

        @Override
        public void onMessage(ByteString bytes) {
            super.onMessage(bytes);
        }

        @Override
        public void onReconnect() {
            super.onReconnect();
        }

        @Override
        public void onClosing(int code, String reason) {
            super.onClosing(code, reason);
        }

        @Override
        public void onClosed(int code, String reason) {
            super.onClosed(code, reason);
        }

        @Override
        public void onFailure(Throwable t, Response response) {
            super.onFailure(t, response);
        }
    };
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
        WsConn wsConn=new WsConn();
        wsConn.connToWsService(this,ws,wsStatusListener,"");

        GradualCircularView gradualCircularView=new GradualCircularView(this);
        int[] colors=new int[]{Color.parseColor("#ff0ff"),Color.parseColor("#ff00f"),Color.parseColor("#ff000")};
        gradualCircularView.isGradud(true,colors);
    }
}
