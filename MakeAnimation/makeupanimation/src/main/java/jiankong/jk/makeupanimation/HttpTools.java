package jiankong.jk.makeupanimation;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class HttpTools {
    public static OkHttpClient getClick(final boolean isHSlog, final String Message,int redTimeOut,int connTimeOut){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (isHSlog==true){
                    Logs.Logshow(Message+"信息:"+message);
                    //提出注册失败结果
                   /* if (message.contains("ms")){
                        Logs.Logshow(Message+"信息2:"+message);
                    }*/
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client=new OkHttpClient.Builder().readTimeout(redTimeOut, TimeUnit.SECONDS).connectTimeout(connTimeOut,TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build();

        return client;
    }
    public  static Retrofit getRetrofit(final boolean isHSlog, String Message,String Url,int redTimeOut,int connTimeOut){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Url).client(getClick(isHSlog,Message,redTimeOut,connTimeOut)).build();
        return retrofit;
    }
}
