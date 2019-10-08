package jiankong.jk.makeupanimation.ws;

import okhttp3.WebSocket;

interface IWManager {
    WebSocket getWebSocket();

    void startConnect();

    void stopConnect();

    boolean isWsConnected();

    int getCurrentStatus();

    void setCurrentStatus(int currentStatus);

    boolean sendMessage(String msg);

}
