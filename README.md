# rabbit

## 特性

* 1 抓取APP接口数据
* 2 使用本地数据测试API
* 3 JSON数据格式化
* 4 实时查看APP页面请求结果
* 5 支持局域网浏览器配置
    
## 实现原理
  
* APP对外公开了一个内容提供者查询要mock数据接口。
* APP对外公开了保存抓取网络数据保存广播协议。
   
* mock-okhhtp是一个对外接口的一个实现库。大家也可以自行实现。
 
## 使用方法

> 方式1: 引用依赖工程mocklib，设置okhttp拦截器即可.
> 
> 方式2: APK可以自己运行源码安装，也可以点击下面地址下载安装
   
## [Demo APK下载](https://fir.im/testpet)

## mocklib拦截器介绍（使用Okhhtp3实现）

    HttpLoggingInterceptor2  格式化日志显示json功能和json数据抓取功能
    MockInterceptor          简易API调用本地测试数据

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