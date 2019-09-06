package jiankong.jk.makeanimation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class SearchEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {
    private int padding=0;
    private boolean isLeft;
    private Drawable[] mDrawables;
    private Drawable drawableLeft,drawableDel;
    private int evenX,evenY;
    private Rect mRect;
    public SearchEditText(Context context) {
        super(context);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (TextUtils.isEmpty(getText().toString())){
            isLeft=hasFocus;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isLeft){
            if (length()<1){

            }
            this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,null,null,null);
            super.onDraw(canvas);
        }else{
            if (mDrawables==null){
                mDrawables=getCompoundDrawables();
            }
            if (drawableLeft==null){
                drawableLeft=mDrawables[0];
            }
            float textWidth=getPaint().measureText(getHint().toString());
            int drawablePadding=getCompoundDrawablePadding();
            int drawableWidth=drawableLeft.getIntrinsicWidth();
            float bodyWidth=textWidth+drawablePadding+drawableWidth;
            canvas.translate((getWidth()-bodyWidth-getPaddingLeft()-getPaddingRight())/2,0);
            super.onDraw(canvas);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                evenX= (int) event.getRawX();
                evenY=(int) event.getRawY();
                if (mRect==null){
                    mRect=new Rect();
                }
                getGlobalVisibleRect(mRect);
                if (mRect.contains(evenX,evenY)){
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
