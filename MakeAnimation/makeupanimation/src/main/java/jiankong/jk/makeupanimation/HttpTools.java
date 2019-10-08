package jiankong.jk.makeupanimation;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class HttpTools {
    public static OkHttpClient getClick(final boolean isHSlog, final String Message){
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
        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        return client;
    }
    public  static Retrofit getRetrofit(final boolean isHSlog, String Message,String Url){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Url).client(getClick(isHSlog,Message)).build();
        return retrofit;
    }
}
