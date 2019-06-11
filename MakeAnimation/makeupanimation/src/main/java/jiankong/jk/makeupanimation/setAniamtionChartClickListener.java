package jiankong.jk.makeupanimation;

import java.util.HashMap;

public interface setAniamtionChartClickListener {
    void onAnimationChartClick(int postion, HashMap<String,Float> locationMap);
    void onDrawFinished();
}
