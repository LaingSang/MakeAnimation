package jiankong.jk.makeupanimation;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

public class ParticleModel {
    //粒子默认宽度
    public static int par_Width=8;
    //随机粒子的位置
    public Random position_Random=new Random();
    //粒子的中心X
    public float center_X;
    //粒子的中心Y
    public float center_Y;
    //粒子的半径
    public float radius;
    //粒子的颜色
    public static int color;
    //粒子的透明度
    public float alpha;
    //粒子的边界
    private Rect mBund;
    public ParticleModel(int color, Rect bund, Point point) {
       int row=point.y;//行
       int cloum=point.x;//列

        this.mBund=bund;
        this.color=color;
        this.alpha=1f;
        this.radius=par_Width;
        this.center_X=bund.left+par_Width*cloum;
        this.center_Y=bund.top+par_Width*row;
    }
    //计算出自己的状态
    public void advance(float factor){
        center_X=center_X+factor*position_Random.nextInt(mBund.width())*(position_Random.nextFloat()-0.5f);
        center_Y=center_Y+factor*position_Random.nextInt(mBund.height() / 2);
        radius=radius-factor*position_Random.nextInt(2);
        alpha=(1f-factor)*(1+position_Random.nextFloat());
    }
}
