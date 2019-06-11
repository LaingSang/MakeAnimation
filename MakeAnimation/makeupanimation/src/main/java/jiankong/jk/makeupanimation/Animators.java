package jiankong.jk.makeupanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Animators implements AnimationInterface {
    /**
     * 浅入浅出平移动画
     */
    @Override
    public void ATanimaotion(final View view, final boolean alpha, final float trX, final float trY, final int duration){
        final AlphaAnimation alianimatoion;
        if (alpha){
            alianimatoion =new AlphaAnimation(1,0);
        }else{
            alianimatoion =new AlphaAnimation(0,1);
        }
        alianimatoion.setDuration(duration);
        alianimatoion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation trani=new TranslateAnimation(0,trX,0,trY);
                trani.setDuration(duration);
                trani.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        final AlphaAnimation alianimatoion;
                        if (alpha){
                            alianimatoion =new AlphaAnimation(0,1);
                        }else{
                            alianimatoion =new AlphaAnimation(1,0);
                        }
                        alianimatoion.setDuration(duration);
                        alianimatoion.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                view.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        view.startAnimation(alianimatoion);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(trani);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(alianimatoion);
    }
    /**
     * 无限原地旋转动画
     */
    @Override
    public void LoopRotaAnimaotion(View view, float frome, float to) {
        final RotateAnimation rotaAnimation=new RotateAnimation(frome,to,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotaAnimation.setDuration(2000);
        rotaAnimation.setRepeatMode(1);
        rotaAnimation.setInterpolator(new LinearInterpolator());
        rotaAnimation.setRepeatCount(-1);
        view.startAnimation(rotaAnimation);
    }

    /**
     * 粒子爆破动画
     */
    @Override
    public void ExplosionAniamtion(final Context context, final View view, final boolean isreturn) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ExplosionField(context).explode(view, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (isreturn){
                                view.setVisibility(View.GONE);
                            }else{

                            }
                        }
                    });
                }
            });
    }
    @Override
    public void ExplosionAniamtion(final Context context, final View view, final boolean isreturn, int postionSize, int color) {
        //大于8低端机运行比较流畅，太小了低端机运行会很卡
        if (postionSize<8){
            return;
        }
        ParticleModel.par_Width=postionSize;
        ParticleModel.color=color;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExplosionField(context).explode(view, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (isreturn){
                            view.setVisibility(View.GONE);
                        }else{

                        }
                    }
                });
            }
        });
    }
    /**
     * 普通平移动画
     */
    @Override
    public void TrAnimation(final View view, float trX, float trY, float frX, float frY, int duration, boolean isEndStop, final boolean isHideShow) {
        TranslateAnimation trani=new TranslateAnimation(frX,trX,frY,trY);
        trani.setDuration(duration);
        trani.setFillAfter(isEndStop);
        trani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isHideShow){
                    view.setVisibility(View.GONE);
                }else{
                    view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(trani);
    }
    /**
     * AnimationChartView设置数据
     */
    @Override
    public void setAnimationChartViewDatas(AnimationChartView acv, List<HashMap<String,String>> datalist,String Title,int durtion) {
        acv.setData(datalist);
        acv.setAnimationDurtion(durtion);
        acv.setTitle(Title);
        //点击事件请在Activity里用
       /* acv.setOnAnimationClick(new setAniamtionChartClickListener() {
            @Override
            public void onAnimationChartClick(int postion, HashMap<String, Float> locationMap) {
                //点击的第几个柱子
            }

            @Override
            public void onDrawFinished() {
                //是否绘制完成
            }
        });*/
    }
    /**
     * AnimationChartView设置数据 （设置具体参数
     */
    @Override
    public void setAnimationChartViewDatas(AnimationChartView acv, List<HashMap<String,String>> datalist, String Title, int durtion, int nameX, int nameSize, int numSize, int numX, int numY, int titleSize, int titleX, int titleY,int unitX,int unitY,int horizontalLineCount) {
        acv.setParameter(nameX,nameSize,numSize,numX,numY,titleSize,titleX,titleY,unitX,unitY,horizontalLineCount);
        acv.setData(datalist);
        acv.setAnimationDurtion(durtion);
        acv.setTitle(Title);
    }

    /**
     * 横轴柱状图
     */
    @Override
    public void HorChartView(HorChartView hcv,List<ChartBean> cblist) {
        hcv.init();
        hcv.setChartList(cblist);
    }

}
