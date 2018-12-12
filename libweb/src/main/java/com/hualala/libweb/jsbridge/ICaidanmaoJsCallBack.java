package com.hualala.libweb.jsbridge;

public interface ICaidanmaoJsCallBack {

    String JSObjectName="webCallCdmInterface";

    //下单
    void placeAnOrder();

    //结账
    void payOrder();

    //是否显示语音按钮
    //show：是否显示，true为显示，false为隐藏
    //x,y为按钮左上角在屏幕中的坐标点，当不显示时，x,y传0
    //width:按钮的宽度，当不显示时，传0
    //height:按钮的高度，当不显示时，传0
    void isShowVoiceButton(boolean show, int x, int y, int width, int height);
}
