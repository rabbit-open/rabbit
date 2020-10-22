# rabbit


## 设计初衷

* 方便查看网络请求数据
* 接口数据和UI显示兼容性测试

## 特性

* 1 抓取APP接口数据
* 2 使用本地数据测试API
* 3 JSON数据格式化
* 4 实时查看APP页面请求结果
* 5 支持局域网浏览器配置
    
## 实现原理
  
* APP对外公开了一个内容提供者查询要mock数据接口。
 
>    APP对外公开的Mock数据测试URI协议:
>    
>    content://com.supets.pet.mockprovider/mockdata
>    
>    支持url参数查询mock数据
     

       
    例子：
        Uri uri = Uri.parse("content://com.supets.pet.mockprovider/mockdata");

        Cursor cursor = ContentResolverCompat.query(AppContext.INSTANCE.getContentResolver(),

        uri, null, null, new String[]{url}, null, null);
    
        try {
            if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex("data"));
            }
        } finally {
          if (cursor != null) {
           cursor.close();
           }
        }

* APP对外公开了保存抓取网络数据保存广播协议。
      
>  APP对外开放的抓取数据广播协议：

>  Intent intent = new Intent("mock.crash.network");

>  intent.putExtra("url", “url地址”);

>  intent.putExtra("requestParam", “post参数”);

>  intent.putExtra("message", "body部分");


       
* mock-okhttp-web是一个对外接口的一个实现库。大家也可以自行实现。
 
## 使用方法

>  方式1:  引用依赖工程mock-okhttp，设置okhttp拦截器即可.
>  方式2:  jcenter() 或者 maven{ url 'https://dl.bintray.com/lihongjiang/maven/'} 
>       
>          debugImplementation "com.supets.pet.mocklib:mock-okhttp-simple:2.12.28"  不需要安装APK,简易可视化，需要悬浮窗权限
>          releaseImplementation "com.supets.pet.mocklib:mock-okhttp-no-op:2.12.28"  
>          releaseImplementation "com.supets.pet.mocklib:mock-okhttp:2.12.28"  需要安装APK
>
>  方式3:  PK可以自己运行源码安装，也可以点击下面地址下载安装
   
## [APK下载](https://fir.im/testpet)

## mocklib拦截器介绍（使用Okhhtp3实现）

    HttpLoggingInterceptor2/3  格式化日志显示json功能和json数据抓取功能
    MockInterceptor          简易API调用本地测试数据

    public class TuziMockManager {
        //本地mock数据编辑
        public static Interceptor getMockInterceptors() {
            return null;
        }
        //参数只是post参数
        public static Interceptor getMockLogInterceptors2() {
            return null;
        }
        //参数包含Header参数，POST参数
        public static Interceptor getMockLogInterceptors3() {
            return null;
        }
        //本地模拟发送请求，可使用于日志可视化，MQTT
        public static void sendLocalRequest(String url, String requestParam, String message) {
        }
    }


## APP和浏览器使用图示

#### 1 翻译助手和实时页面抓取助手

<img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone3.png" width = "360" /><img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone13.png" width = "360" />  

#### 2 测试APP部分界面
    
<img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone1.png" width = "360" /><img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone2.png" width = "360" />
<img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone9.png" width = "360" />  <img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone4.png" width = "360" />  
<img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone5.png" width = "360" /><img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone6.png" width = "360" />  
<img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone7.png" width = "360" /><img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone8.png" width = "360" />
<img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone10.png" width = "360" /><img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone11.png" width = "360" />
<img src="https://github.com/rabbit-open/rabbit/blob/master/database/phone12.png" width = "360" />

#### 3 网页输入地址：http://127.0.0.1:54321

<img src="https://github.com/rabbit-open/rabbit/blob/master/database/web1.png"  />

<img src="https://github.com/rabbit-open/rabbit/blob/master/database/web2.png"  />

<img src="https://github.com/rabbit-open/rabbit/blob/master/database/web3.png"  />  


## 版权声明

    此项目属于个人业余项目，严禁商业用途。