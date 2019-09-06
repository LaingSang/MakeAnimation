package jiankong.jk.makeanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class GradualCircularView extends View {
    private Paint paintBK;
    private Paint paintNR;
    private boolean isGradua=false;
    private int color[]={0xff2D0081,0xff8B3097,0xffD14E7A};
    public GradualCircularView(Context context) {
        super(context);
        init();
    }

    public GradualCircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradualCircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        paintBK=new Paint();
        paintNR=new Paint();
        paintBK.setColor(Color.WHITE);
        paintBK.setAntiAlias(true);
        paintBK.setStrokeWidth(10);
        paintBK.setStyle(Paint.Style.STROKE);
        paintBK.setTextSize(30);

        paintNR.setColor(Color.RED);
        paintNR.setAntiAlias(true);
        paintNR.setStyle(Paint.Style.FILL);
        paintNR.setTextSize(30);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int miniwidth=getSuggestedMinimumWidth();
        int miniheight=getSuggestedMinimumHeight();
        int width=mesuerWidth(miniwidth,widthMeasureSpec);
        int height=mesuerHeight(miniheight,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }
    private int mesuerWidth(int defw,int mespc){
        int specMode=MeasureSpec.getMode(mespc);
        int specSize=MeasureSpec.getSize(mespc);

        switch (specMode){
            case MeasureSpec.AT_MOST:
                defw=Math.min(defw, specSize);
                break;
            case MeasureSpec.EXACTLY:
                defw=specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defw=Math.max(defw,specSize);
                 break;
        }
        return defw;
    }
    private int mesuerHeight(int defh,int mespc){
        int specMode=MeasureSpec.getMode(mespc);
        int specSize=MeasureSpec.getSize(mespc);

        switch (specMode){
            case MeasureSpec.AT_MOST:
                defh=Math.min(defh, specSize);
                break;
            case MeasureSpec.EXACTLY:
                defh=specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defh=Math.max(defh,specSize);
                break;
        }
        return defh;
    }
    public void isGradud(boolean isGradua,int[] colors){
        this.isGradua=isGradua;
        this.color=colors;
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isGradua==false){
            canvas.drawRoundRect(new RectF(0,0,getMeasuredWidth(),getMeasuredHeight()), 40, 40, paintBK);
        }else{
            LinearGradient shader=new LinearGradient(0,0,getMeasuredWidth(),0,color,null, Shader.TileMode.CLAMP);
            paintNR.setShader(shader);
            canvas.drawRoundRect(new RectF(0,0,getMeasuredWidth(),getMeasuredHeight()), 50, 50, paintNR);
        }

    }

}
