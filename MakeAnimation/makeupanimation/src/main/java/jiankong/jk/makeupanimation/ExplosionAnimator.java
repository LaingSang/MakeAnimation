package jiankong.jk.makeupanimation;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class ExplosionAnimator extends ValueAnimator {
    //动画持续时间
    private static final int DEFAULT_DURATION = 1000;
    private ParticleModel[][] mParticles;
    private Paint mPaint;
    private View mContainer;

    public ExplosionAnimator(View view, Bitmap bitmap, Rect bound) {
        setFloatValues(0.0f, 1.0f);
        setDuration(DEFAULT_DURATION);

        mPaint = new Paint();
        mContainer = view;
        mParticles = generateParticles(bitmap, bound);
    }

    // 生成粒子，按行按列生成全部粒子
    private ParticleModel[][] generateParticles(Bitmap bitmap, Rect bound) {
        int w = bound.width();
        int h = bound.height();

        // 横向粒子的个数
        int horizontalCount = w / ParticleModel.par_Width;
        // 竖向粒子的个数
        int verticalCount = h / ParticleModel.par_Width;

        // 粒子宽度
        int bitmapPartWidth = bitmap.getWidth() / horizontalCount;
        // 粒子高度
        int bitmapPartHeight = bitmap.getHeight() / verticalCount;

        ParticleModel[][] particles = new ParticleModel[verticalCount][horizontalCount];
        for (int row = 0; row < verticalCount; row++) {
            for (int column = 0; column < horizontalCount; column++) {
                //取得当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmapPartWidth, row * bitmapPartHeight);

                Point point = new Point(column, row);
                particles[row][column] = new ParticleModel(color, bound, point);
            }
        }
        return particles;
    }

    // 由view调用，在View上绘制全部的粒子
    void draw(Canvas canvas) {
        // 动画结束时停止
        if (!isStarted()) {
            return;
        }
        // 遍历粒子，并绘制在View上
        for (ParticleModel[] particle : mParticles) {
            for (ParticleModel p : particle) {
                p.advance((Float) getAnimatedValue());
                mPaint.setColor(p.color);
                // 错误的设置方式只是这样设置，透明色会显示为黑色
                // mPaint.setAlpha((int) (255 * p.alpha));
                // 正确的设置方式，这样透明颜色就不是黑色了
                mPaint.setAlpha((int) (Color.alpha(p.color) * p.alpha));
                canvas.drawCircle(p.center_X, p.center_Y, p.radius, mPaint);
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
