package jiankong.jk.makeupanimation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

public class HorChartView extends View {
    private float maxVaule;
    private List<ChartBean> typeList;
    private int chartWidth;
    private int typeWidth;
    private int lineStorkeWidth;
    private Rect typeRect,topRect;
    private Path txtPath;
    private int typeSpace;
    private int itemNameWidth;
    private int betweenMargin;
    private int scoreTextHeight;
    private Paint typePaint,linePaint,textPaint,numTextPaint;
    public HorChartView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }
    public void init(){
        typePaint=new Paint();
        typePaint.setColor(Color.parseColor("#ff00ff"));

        linePaint=new Paint();
        linePaint.setColor(Color.parseColor("#00ffff"));
        lineStorkeWidth=dp2px(0.5f);
        linePaint.setStrokeWidth(lineStorkeWidth);

        textPaint=new Paint();
        textPaint.setColor(Color.parseColor("#d3d3d3"));
        textPaint.setTextSize(dp2px(13));
        textPaint.setAntiAlias(true);

        numTextPaint=new Paint();
        numTextPaint.setColor(Color.parseColor("#cccccc"));
        numTextPaint.setTextSize(dp2px(13));

        typeRect=new Rect(0,0,0,0);
        txtPath=new Path();

        typeWidth=dp2px(18);
        typeSpace=dp2px(20);
        scoreTextHeight=dp2px(13);
        itemNameWidth=172;
        betweenMargin=scoreTextHeight/2;

    }
    public void init(int typeColor,int lineColor,int textColor,int numColor){
        typePaint=new Paint();
        typePaint.setColor(typeColor);

        linePaint=new Paint();
        linePaint.setColor(lineColor);
        lineStorkeWidth=dp2px(0.5f);
        linePaint.setStrokeWidth(lineStorkeWidth);

        textPaint=new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(dp2px(13));
        textPaint.setAntiAlias(true);

        numTextPaint=new Paint();
        numTextPaint.setColor(numColor);
        numTextPaint.setTextSize(dp2px(13));

        typeRect=new Rect(0,0,0,0);
        txtPath=new Path();

        typeWidth=dp2px(18);
        typeSpace=dp2px(20);
        scoreTextHeight=dp2px(13);
        itemNameWidth=172;
        betweenMargin=scoreTextHeight/2;

    }
    public static int dp2px(double dpi) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dpi + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lineViewWidth=(float)((this.getWidth()-itemNameWidth)*0.8);
        float scoreWidth=lineViewWidth/5;
        int scoreAdd=(int)(maxVaule/5);
        if (isInEditMode()){
            return;
        }
        for (int i=0;i<typeList.size();i++){
            typeRect.left = itemNameWidth;
            typeRect.top = typeSpace * (i + 2) + typeWidth * i;
            typeRect.right = (int) (lineViewWidth * (typeList.get(i).getScore() / maxVaule)) + itemNameWidth;
            typeRect.bottom = typeRect.top + typeWidth;
            //当值小于多少的时候改变颜色
            /*if ((typeList.get(i).getScore()/maxVaule)>=0.6){
                typePaint.setColor(Color.parseColor("#00ffff"));
            }else{
                typePaint.setColor(Color.parseColor("#ff00ff"));
            }*/
            canvas.drawRect(typeRect,typePaint);
            canvas.drawText(typeList.get(i).getScore()+"",typeRect.right,typeRect.bottom-(typeWidth-scoreTextHeight),textPaint);
            canvas.drawText(typeList.get(i).getTitle(),itemNameWidth-betweenMargin- textPaint.measureText(typeList.get(i).getTitle()), typeRect.bottom - (typeWidth - scoreTextHeight),textPaint);
            canvas.drawText(String.valueOf(scoreAdd * (i + 1)), itemNameWidth + scoreWidth * (i + 1) - textPaint.measureText(String.valueOf(scoreAdd * (i + 1))) / 2, typeSpace, textPaint);
        }
        canvas.drawText("0", itemNameWidth - betweenMargin - textPaint.measureText("0"), typeSpace, textPaint);
        canvas.drawLine(itemNameWidth, 0, itemNameWidth, this.getHeight(), linePaint);
    }
    public void setChartList(List<ChartBean> list){
        this.typeList=list;
        if (list==null){
            throw new RuntimeException("BarChartView.setItems(): the param items cannot be null.");
        }
        if (list.size()==0){
            return;
        }
        maxVaule=typeList.get(0).getScore();
        for (ChartBean cb:list){
            if (cb.getScore()>maxVaule){
                maxVaule=cb.getScore();
            }
        }
        invalidate();
    }
    private int measureWidth(int measure){
        int specMode=MeasureSpec.getMode(measure);
        int specSize=MeasureSpec.getSize(measure);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {

        }
        //match_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {

        }
        return specSize;
    }
    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {

        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {

        }
        return specSize;
    }
}
