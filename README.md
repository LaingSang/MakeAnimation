# MakeAnimation
个人使用的一些动画 目前只集合了淡入淡出平移动画，原地无限旋转动画，粒子爆破动画，一个带动画的柱状图，圆形图片view等功能
 ## 4.0添加内容
 首先导入
   implementation 'com.squareup.retrofit2:retrofit:2.1.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.4.1'
    
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
      dependencies {
	        implementation 'com.github.LaingSang.MakeAnimation:makeupanimation:4.0'
	}
 添加了Ws快速链接方法,Retrofit快速设置方法和一大堆实用的工具类具体使用方法如下
  ## Ws调用
     private WSManager ws;
     private WsStatusListener wsStatusListener=new WsStatusListener() {
        @Override
        public void onOpen(okhttp3.Response response) {
            Logs.Logshow("链接成功");

        }
        @Override
        public void onMessage(String text) {
            Logs.Logshow("收到信息:"+text);
        }

        @Override
        public void onMessage(ByteString bytes) {
            super.onMessage(bytes);
        }

        @Override
        public void onReconnect() {
            Logs.Logshow("服务器重连");
        }

        @Override
        public void onClosing(int code, String reason) {
            Logs.Logshow("服务器关闭中");
        }

        @Override
        public void onClosed(int code, String reason) {
            Logs.Logshow("服务器已关闭");
        }

        @Override
        public void onFailure(Throwable t, okhttp3.Response response) {
            Logs.Logshow("服务器连接失败");
        }
    };
    
       WsConn wsConn=new WsConn();
        wsConn.connToWsService(this,ws,wsStatusListener,"");
 ## Retrofit调用
	你自己定义的API xmRetrofitService = ChatRoomTools.getRetrofit(true(是否显示), "你自己定义的Log").create(你自己定义的API.class);
        Call<ResponseBody> call = xmRetrofitService.xxx(xxx,xxx,xxx);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string().trim();
                    Logs.Logshow("返回的值"+s);
                    JSONObject jos=new JSONObject(s);
                    if (jos.optString("success").equals("true")){

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
## 主要使用方法为
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
 	dependencies  {
			implementation 'com.github.LaingSang:MakeAnimation:V1.0'
	}
 
调用比较简单只需要

![图片失效](img/shiyong.png)


![图片失效](img/zhushi.png)

实例化这个类然后调用里面的方法就行了

动画示例

![图片失效](img/dh2.gif)

横向柱子图的使用方法

![图片失效](img/horzhuzi.png)

柱子图的使用方法

![图片失效](img/zhuzishiyong.png)

柱子图示例

![图片失效](img/zhuzi2.gif)
