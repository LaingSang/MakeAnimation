package jiankong.jk.makeupanimation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class WebPage extends Activity {
    //加载网址的Webview
    private WebView web;
    //控制是否加http
    private boolean sws = true;
    //控制保存图片按钮出现的位置
    private int saveX;
    private int saveY;
    private Animators animators=new Animators();
    private ImageView loading;
    private RelativeLayout loading_rea;
    //保存图片
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String picFile = (String) msg.obj;
            String[] split = picFile.split("/");
            String fileName = split[split.length-1];
            try {
                MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), picFile, fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + picFile)));
            Toast.makeText(WebPage.this,"图片保存图库成功",Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init();
    }
    private void init(){

        initView();
    }
    //初始化view
    private void initView(){
        loading_rea=findViewById(R.id.loading_rea);
        loading_rea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        loading=findViewById(R.id.loading);
        animators.LoopRotaAnimaotion(loading,-360,360);
        web = findViewById(R.id.xm_web);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        //进入就加载的网址
        Urls(bundle.getString("Urls"),web);
    }
    public void setLoadIcon(int bg){
        loading.setBackgroundResource(bg);
    }
    //初始化Web
    private void initWeb(WebView wbv){
        // TODO Auto-generated method stub
        wbv.setInitialScale(5);
        WebSettings webSettings = wbv.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(false); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        wbv.clearCache(true);
    }
    //启用Webview
    @SuppressLint("SetJavaScriptEnabled")
    private void Urls(String path,final WebView wbv){
        initWeb(wbv);
        wbv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final WebView.HitTestResult hitTestResult = wbv.getHitTestResult();
                if(hitTestResult.getType()== WebView.HitTestResult.IMAGE_TYPE||hitTestResult.getType()== WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){
                    popupwindowSave(view);
                }
                return false;
            }
        });

        wbv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
              //  Log.e("协议",url);
                if(url.startsWith("intent")||url.startsWith("youku")||url.startsWith("bilibili")){

                    return true;
                }else{
                    return super.shouldOverrideUrlLoading(view, url);
                }

            }
        });
        wbv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                saveX=(int)motionEvent.getX();
                saveY=(int)motionEvent.getY();
                return false;
            }
        });

        wbv.loadUrl(path);
        wbv.getSettings().setBlockNetworkImage(false);
        loading_rea.setVisibility(View.GONE);
    }

    /*if (url.contains("alipayqr://")){
              boolean isAli=isAlipayQR();
              if (isAli){
                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                  startActivity(intent);
              }
          }else if (url.contains("alipays://")){
              boolean isAli=isAlipay();
              if (isAli){
                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                  startActivity(intent);
              }
          }else if (url.startsWith("newtab:")){
              url=url.replace("newtab:","");
              view.loadUrl(url);
          }else if (url.startsWith("intent")||url.startsWith("youku")){
              return super.shouldOverrideUrlLoading(view, url);
          }else{
              view.loadUrl(url);
          }
*/
    //适配支付宝协议
    private boolean isAlipay(){
        Uri uri=Uri.parse("alipays://platformapi/startApp");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        ComponentName cname=intent.resolveActivity(getPackageManager());
        return  cname!=null;

    }
    private boolean isAlipayQR(){
        Uri uri=Uri.parse("alipayqr://platformapi/startApp");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        ComponentName cname=intent.resolveActivity(getPackageManager());
        return  cname!=null;

    }
    //保存按钮
    private void popupwindowSave(View view) {
        final View v = LayoutInflater.from(WebPage.this).inflate(R.layout.popupwindow_save, null);
        TextView txtsave = v.findViewById(R.id.saveimg);
        final WebView.HitTestResult hitTestResult = web.getHitTestResult();
        final PopupWindow save;
        save = new PopupWindow(dip2px(120), dip2px(40));
        save.setContentView(v);
        save.setFocusable(true);
        save.setOutsideTouchable(true);
        save.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        save.showAtLocation(view, Gravity.TOP | Gravity.LEFT, saveX, saveY + 10);

        txtsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WebPage.this);
                builder.setTitle("提示");
                builder.setMessage("保存图片到本地");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url = hitTestResult.getExtra();
                        // 下载图片到本地
                        DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener(){

                            @Override
                            public void getDownPath(String s) {
                                Toast.makeText(WebPage.this,"下载完成",Toast.LENGTH_LONG).show();
                                Message msg = Message.obtain();
                                msg.obj=s;
                                handler.sendMessage(msg);
                            }
                        });

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    // 自动dismiss
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                save.dismiss();
            }
        });


    }
    public int dip2px( float dipValue){
        float scale = WebPage.this.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
}
