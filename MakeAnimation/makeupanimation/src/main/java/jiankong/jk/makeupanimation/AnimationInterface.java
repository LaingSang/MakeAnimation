package jiankong.jk.makeupanimation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import java.util.HashMap;
import java.util.List;

public interface AnimationInterface  {
      /**
       * 浅入浅出平移动画
       */
      void ATanimaotion(final View view, boolean alpha, final float trX, final float trY,int duration);
      /**
       * 无限原地旋转动画
       */
      void LoopRotaAnimaotion(final View view,final float frome, final float to);
      /**
       * 粒子爆破动画
       */
      void ExplosionAniamtion(Context context,View view,boolean isreturn);
      void ExplosionAniamtion(Context context, View view, boolean isreturn, int postionSize, int color);
      /**
       * 普通平移动画
       */
      void TrAnimation(View view,float trX,float trY,float frX,float frY,int duration,boolean isEndStop,boolean isHideShow);
      /**
       * AnimationChartView设置数据
       */
      void setAnimationChartViewDatas(AnimationChartView acv, List<HashMap<String,String>> datalist,String Title,int durtion);
      void setAnimationChartViewDatas(AnimationChartView acv, List<HashMap<String,String>> datalist,String Title,int durtion,int nameX
                                          ,int nameSize,int numSize,int numX,int numY,int titleSize,int titleX,int titleY,int unitX
                                          ,int unitY,int horizontalLineCount);
      /**
       * 横轴柱状图
       */
      void HorChartView(HorChartView hcv,List<ChartBean> cblist);
}
