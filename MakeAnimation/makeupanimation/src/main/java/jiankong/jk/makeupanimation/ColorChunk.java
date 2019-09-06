package jiankong.jk.makeupanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ColorChunk extends View {
    private Paint paintBg;
    private Paint paintBk;
    private boolean isClick=false,ishuizhi=false;
    private int color=Color.BLACK;
    private OnColorChunkOnClickListner colorChunkOnClickListner;
    public ColorChunk(Context context) {
        super(context);
        init();
    }

    public ColorChunk(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorChunk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        paintBg=new Paint();
        paintBg.setStrokeWidth(11);
        paintBg.setColor(color);
        paintBg.setStyle(Paint.Style.FILL);

        paintBk=new Paint();
        paintBk.setStrokeWidth(11);
        paintBk.setStyle(Paint.Style.STROKE);
        paintBk.setColor(Color.parseColor("#2C33F5"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isClick==true){
            canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paintBk);
            canvas.drawRect(5,5,getMeasuredWidth()-5,getMeasuredHeight()-5,paintBg);
        }else{
            canvas.drawRect(8,8,getMeasuredWidth()-7,getMeasuredHeight()-8,paintBg);
        }
    }

  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (isClick==true){
                    isClick=false;
                }else  if (isClick==false){
                    isClick=true;
                }
                if(colorChunkOnClickListner!=null){
                    colorChunkOnClickListner.colorChunkClick(color);
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }*/
    public void setColor(int color){
        this.color=color;
        paintBg.setColor(color);
    }
    public void setClick(boolean isClick){
        this.isClick=isClick;
        invalidate();
    }
    public void setColorChunkOnClickListner(OnColorChunkOnClickListner listner){
        this.colorChunkOnClickListner=listner;
    }
    public interface OnColorChunkOnClickListner{
         void colorChunkClick(int color);
    }
}
