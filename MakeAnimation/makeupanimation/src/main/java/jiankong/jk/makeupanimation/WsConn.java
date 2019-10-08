package jiankong.jk.makeupanimation;

import android.app.Activity;

import java.util.concurrent.TimeUnit;

import jiankong.jk.makeupanimation.ws.WSManager;
import jiankong.jk.makeupanimation.ws.WsStatusListener;
import okhttp3.OkHttpClient;

public class WsConn {
    public void connToWsService(Activity a, WSManager wsManager, WsStatusListener wsStatusListener, String wsUrl){
        if (wsManager!=null){
            wsManager.stopConnect();
            wsManager=null;
        }
        wsManager=new WSManager.Builder(a)
                .client(new OkHttpClient().newBuilder(). pingInterval(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true).build())
                .needReconnect(true)
                .wsUrl(wsUrl).build();
        wsManager.setWsStatusListener(wsStatusListener);
        wsManager.startConnect();
    }
}
