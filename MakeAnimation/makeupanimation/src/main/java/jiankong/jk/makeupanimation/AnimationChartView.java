package jiankong.jk.makeupanimation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AnimationChartView extends View {
    private Thread mThread;
    //动画时间
    private int durtion=1500;
    //设置标题
    private String title="无";
    //设置柱状图画笔
    private Paint acPaint;
    private String emptyWarning = "";
    private Context mContext;
    //设置位置list数组
    private List<HashMap<String,Float>> locationList;
    //数据list
    private List<HashMap<String,String>> acdataList;
    //判断是否开始
    private boolean run;
    //设置颜色
    private int[] color;
    //随机颜色
    private Random random;
    //设置回调
    private setAniamtionChartClickListener onAclistener;
    //设置View的宽高
    private int width,height;
    //坐标原点边界
    private int distance=30;
    //设置箭头宽度
    private int arrWidth=5;
    //水平线间隔
    private float horizontalLineInterVal;
    //柱状图宽度,柱状图间隔
    private float ChartWidth,chartInterVal;
    //密度 柱子指标
    private float density,zhuziValue=4.5f;
    //存放所有的delta
    private float[] deltas;
    //最大值出现的位置
    private int maxValuePosition;
    //控制表图参数
    private int nameX=-5,nameSize=30,numSize=25,numX=20,numY=-5,titleSize=60,titleX=50,titleY=10,unitX=0,unitY=0,horizontalLineCount=10;
    public AnimationChartView(Context context) {
        super(context);
        init(context);
    }

    public AnimationChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnimationChartView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        acPaint = new Paint();
        locationList = new ArrayList<>();
        run = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setWillNotDraw(false);
        if (isDataNull()){
            canvas.drawColor(Color.GRAY);
            acPaint.setColor(Color.MAGENTA);
            acPaint.setTextSize(numSize);
            canvas.drawText(emptyWarning, width / 2 - acPaint.getTextSize()*emptyWarning.length()/2, height / 2-acPaint.getTextSize()/2, acPaint);
        }else{
            acPaint.setColor(Color.BLACK);
            acPaint.setStrokeWidth(4);
            //绘制x轴
            canvas.drawLine(distance * density, height - distance * density, width - distance * density, height - distance * density, acPaint);
            //绘制x轴箭头
            canvas.drawLine(width - distance * density-arrWidth * density, height - distance * density-arrWidth * density, width - distance * density, height - distance * density, acPaint);
            canvas.drawLine(width - distance * density - arrWidth * density, height - distance * density + arrWidth * density, width - distance * density, height - distance * density, acPaint);
            //绘制y轴
            canvas.drawLine(distance * density, distance * density, distance * density, (height - distance * density), acPaint);
            //绘制y轴箭头
            canvas.drawLine(distance * density - arrWidth * density, distance * density + arrWidth * density, distance * density, distance * density, acPaint);
            canvas.drawLine(distance * density + arrWidth * density, distance * density + arrWidth * density, distance * density, distance * density, acPaint);
            //绘制底部阶梯标准线  设置线的位置
            horizontalLineInterVal = (height - 2 * distance * density) / horizontalLineCount-zhuziValue;//+XX或者-XX
            acPaint.setColor(Color.GRAY);
            acPaint.setStrokeWidth(1);
            float[] arr = getMaxValue(acdataList);
            if (arr[1] == 0){ return;
            } float average = arr[0] / (horizontalLineCount);
            acPaint.setTextSize(numSize);
            for (int i = 1 ;i<=horizontalLineCount;i++){
                canvas.drawLine(distance * density, height - distance * density - horizontalLineInterVal * i, width - distance * density, height - distance * density - horizontalLineInterVal * i,acPaint);
                canvas.drawText(average * i+"",5f,(height - distance * density - horizontalLineInterVal * i + acPaint.getTextSize()/2)-zhuziValue,acPaint);
            }
            //设置0的位置
            canvas.drawText("0", 30f, height - distance * density + acPaint.getTextSize() / 2, acPaint);
            acPaint.setStrokeWidth(3f);
            chartInterVal = (width - 2 * distance * density)/ acdataList.size();
            ChartWidth = chartInterVal/3;
            locationList.clear();
            for (int i = 0;i< acdataList.size();i++){
                acPaint.setColor(color[i]);
                float value = Float.parseFloat(acdataList.get(i).get("value"));
                //从上到下的动画
//                canvas.drawRect(distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 - ZhuZhuangTuWidth / 2,
//                        height - distance * density - (height - 2 * distance * density) * (value / arr[0]),
//                        distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 + ZhuZhuangTuWidth / 2
//                        , height - distance * density - delta, mPaint);
                //从下到上的动画,所有的图形的绘制时间都是一样的(delta一样),也就是说所有柱子同时绘制完成,长的绘制早,短的绘制晚. Height-X
                canvas.drawRect(distance * density + chartInterVal * i + chartInterVal / 2 - ChartWidth / 2,
                        height - distance * density - (height - 2.5f * distance * density) * (value / arr[0]) + deltas[i],
                        distance * density + chartInterVal * i + chartInterVal / 2 + ChartWidth / 2
                        , height - distance * density, acPaint);
                //若要所有的柱子同时开始绘制,需要给每个柱子添加一个delta.并且要对动画时间分别控制.
                HashMap<String,Float> map = new HashMap<>();
                map.put("left",distance * density + chartInterVal * i + chartInterVal / 2 - ChartWidth / 2);
                map.put("top",height - distance * density - (height - 2 * distance * density) * (value / arr[0]));
                map.put("right",distance * density + chartInterVal * i + chartInterVal / 2 + ChartWidth / 2);
                map.put("bottom",height - distance * density + deltas[i]);
                locationList.add(map);
                acPaint.setColor(Color.BLACK);
                acPaint.setTextSize(nameSize);
                String name = acdataList.get(i).get("name");
                //设置底部名字的位置
                canvas.drawText(name,(distance* density+chartInterVal*i+chartInterVal/2-(name.length()/2)*acPaint.getTextSize())+nameX,
                        height-distance* density+acPaint.getTextSize(),acPaint);
                String unit = acdataList.get(i).get("unit");
                String describe = value+unit;
                //绘制柱状图上的字
                canvas.drawText(describe,
                        (distance* density+chartInterVal*i+chartInterVal/2-ChartWidth/2)-numX,
                        (height - distance * density - (height - 2 * distance * density) * (value / arr[0]))-numY,acPaint);
            } acPaint.setColor(Color.BLACK);
            //设置单位的位置
            canvas.drawText("单位:"+acdataList.get(0).get("unit"),unitX,(distance* density/2)+unitY,acPaint);
            //设置标题参数
            acPaint.setTextSize(titleSize);
            canvas.drawText(title,(width/2-title.length()/2*acPaint.getTextSize())+titleX,(distance* density)-titleY,acPaint);
        } if (stop(deltas) && !mThread.isAlive()) {
            if (null != onAclistener){
                onAclistener.onDrawFinished();
            }
        }
    }
    public void setParameter(int nameX,int nameSize,int numSize,int numX,int numY,int titleSize,int titleX,int titleY,int unitX,int unitY,int horizontalLineCount){
        this.nameX=nameX;
        this.nameSize=nameSize;
        this.numSize=numSize;
        this.numX=numX;
        this.numY=numY;
        this.titleSize=titleSize;
        this.titleX=titleX;
        this.titleY=titleY;
        this.unitX=unitX;
        this.unitY=unitY;
        this.horizontalLineCount=horizontalLineCount;
    }
    private boolean isDataNull(){
        if (null == acdataList || acdataList.size() ==0){
            return true;
        }else{
            return false;
        }
    }
    //判断是否绘制完毕
    private boolean stop(float[] arr) {
        for(float f : arr){
            if (f != 0){
                 return false;
            }
        } return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                for (int i = 0;i<locationList.size();i++){
                    if (x>=locationList.get(i).get("left") && x<=locationList.get(i).get("right") && y >=locationList.get(i).get("top")
                            && y<=locationList.get(i).get("bottom")){
                        if (null != onAclistener){
                            onAclistener.onAnimationChartClick(i,locationList.get(i));
                        }
                    }
                }
                break;
    }
    return super.onTouchEvent(event);
    }
    //获取最大值
    private float[] getMaxValue(List<HashMap<String,String>> list) {
        try {
            float max = Float.parseFloat(list.get(0).get("value"));
            for (int i = 0;i<list.size();i++){
                float temp = Float.parseFloat(list.get(i).get("value"));
                if (temp > max){
                    max = temp;
                }
            }
            return new float[]{max,1};
        }catch (Exception e){
            return new float[]{-1,0};
        }
    }
    //获取最大值的位置
    private int getMaxValuePosition(List<HashMap<String,String>> list){
        try {
            int j = 0;
            float max = Float.parseFloat(list.get(0).get("value"));
            for (int i = 0;i<list.size();i++){
                float temp = Float.parseFloat(list.get(i).get("value"));
                if (temp > max){
                    j = i;
                }
            }
            return j;
        }catch (Exception e){
                return 0;
        }
    }
    //设置数据
    public void setData(List<HashMap<String,String>> list) {
        if (null == list || list.size() ==0){
            return;
        }
        this.acdataList = list;
        color = new int[acdataList.size()];
        random = new Random();
        for (int i = 0;i<acdataList.size();i++){ int r = random.nextInt(256);
            int g= random.nextInt(256);
            int b = random.nextInt(256);
            int colo = Color.rgb(r, g, b);
            color[i] = colo;
        }
    }
    //设置动画时间
    public void setAnimationDurtion(int durtion){
        this.durtion = durtion;
    }
    //判断集合是否为空
    private boolean isDataMapEmpty(){
        if (null == acdataList || acdataList.size() ==0){
            return true;
        }else{
                return false;
        }
    }
    //设置View的点击事件
    public void setOnAnimationClick(setAniamtionChartClickListener mListener){
        this.onAclistener = mListener;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
        density = getDensity();
        maxValuePosition = getMaxValuePosition(acdataList);
        // delta = height - 2*distance*density;
        //获取所有的delta
        if (!isDataMapEmpty()){
            deltas = new float[acdataList.size()];
            float m = getMaxValue(acdataList)[0];
            for (int i =0;i<acdataList.size();i++){ float v = Float.parseFloat(acdataList.get(i).get("value"));
                float delta = (height - 2*distance*getDensity())*(v/m);
                deltas[i] = delta;
            }
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
    @SuppressLint("DrawAllocation")
    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (run){
                    try {
                        Thread.sleep(15);
                        for (int i =0;i<deltas.length;i++){
                            //做向下的动画时使用
                            // delta = delta - (height - 2*distance*density)/(millions/20);
                            float delta = deltas[i];
                            float m = getMaxValue(acdataList)[0];
                            float v = Float.parseFloat(acdataList.get(i).get("value"));
                            float time = durtion*(v/m);
                            if (time <= 800){ time = 800;
                            }
                            delta = delta - (height - 1*distance*density)*(v /m)/(time/15);
                            if (delta <=0){ delta = 0;
                                if (i==maxValuePosition)
                                {
                                    run = false;
                                }
                            }
                            deltas[i] = delta;
                        }
                        postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }
    private float getDensity(){
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }
    //设置Title
    public void setTitle(String title) {
        this.title = title;
    }
}

