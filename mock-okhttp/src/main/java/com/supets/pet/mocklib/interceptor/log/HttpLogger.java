package com.supets.pet.mocklib.interceptor.log;

import android.util.Log;

public class HttpLogger implements HttpLoggingInterceptor.Logger {

    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(String message) {
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
        }
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        if (message.startsWith("<-- END HTTP")) {
            Log.d("[ok-http]", mMessage.toString());
        }
    }
}
