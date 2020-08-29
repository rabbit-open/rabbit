/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.supets.pet.mocklib.mock;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>Example:</p>
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>Example:</p>
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>Example:</p>
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String url, String message);

        void log2(String url, String content, String postParam, String headerParam);

        void log3(String url, String messgae);

        /**
         * A {@link Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger() {


            @Override
            public void log(String url, String message) {
                JsonFormatUtils.log(url, message);
            }

            @Override
            public void log2(String url, String message, String postParam, String headerParam) {
                JsonFormatUtils.logSave(url, message, postParam, headerParam);
            }

            @Override
            public void log3(String url, String messgae) {
                JsonFormatUtils.logFile(url, messgae);
            }
        };


    }

    public HttpLoggingInterceptor() {
        this(Logger.DEFAULT);
        setLevel(Level.BODY);
        this.requestResponse = true;
    }

    public HttpLoggingInterceptor(Logger logger) {
        setLevel(Level.BODY);
        this.logger = logger;
        this.requestResponse = true;
    }

    public HttpLoggingInterceptor(Logger logger, boolean requestResponse) {
        setLevel(Level.BODY);
        this.logger = logger;
        this.requestResponse = requestResponse;
    }

    private final Logger logger;

    private volatile Level level = Level.NONE;

    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    private StringBuilder sb = new StringBuilder();
    private String postParam = "";
    private String headerParam = "";

    private boolean requestResponse = false;//打开获取response


    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;
        sb.setLength(0);

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }

        logger.log(request.url().toString(), requestStartMessage);
        sb.append(requestStartMessage).append("\n");

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    logger.log(request.url().toString(), "Content-Type: " + requestBody.contentType());
                    sb.append("Content-Type: " + requestBody.contentType()).append("\n");
                }
                if (requestBody.contentLength() != -1) {
                    logger.log(request.url().toString(), "Content-Length: " + requestBody.contentLength());
                    sb.append("Content-Length: " + requestBody.contentLength()).append("\n");
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                //TODO  Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logger.log(request.url().toString(), name + ": " + headers.value(i));
                    sb.append(name + ": " + headers.value(i)).append("\n");
                    //TODO header param
                    headerParam = headerParam + ("" + name + "=" + headers.value(i)) + "&";
                }
            }

            if (!logBody || !hasRequestBody) {
                logger.log(request.url().toString(), "--> END " + request.method());
                sb.append("--> END " + request.method()).append("\n");
            } else if (bodyEncoded(request.headers())) {
                logger.log(request.url().toString(), "--> END " + request.method() + " (encoded body omitted)");
                sb.append("--> END " + request.method() + " (encoded body omitted)").append("\n");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                logger.log(request.url().toString(), "");
                sb.append("").append("\n");

                if (isPlaintext(buffer)) {
                    //TODO 请求POST参数
                    postParam = buffer.readString(charset);

                    logger.log(request.url().toString(), postParam);
                    sb.append(postParam).append("\n");
                    logger.log(request.url().toString(), "--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                    sb.append("--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)").append("\n");
                } else {
                    logger.log(request.url().toString(), "--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                    sb.append("--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)").append("\n");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logger.log(request.url().toString(), "<-- HTTP FAILED: " + e);
            sb.append("<-- HTTP FAILED: " + e).append("\n");
            postParam = "";
            headerParam = "";
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        String log = "<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!logHeaders ? ", "
                + bodySize + " body" : "") + ')';
        logger.log(request.url().toString(), log);
        sb.append(log).append("\n");

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                logger.log(request.url().toString(), headers.name(i) + ": " + headers.value(i));
                sb.append(headers.name(i) + ": " + headers.value(i)).append("\n");
                //TODO response  param
                if (requestResponse) {
                    headerParam = headerParam + ("" + headers.name(i) + "=" + headers.value(i)) + "&";
                }
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                logger.log(request.url().toString(), "<-- END HTTP");
                sb.append("<-- END HTTP").append("\n");

            } else if (bodyEncoded(response.headers())) {
                logger.log(request.url().toString(), "<-- END HTTP (encoded body omitted)");
                sb.append("<-- END HTTP (encoded body omitted)").append("\n");

            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        logger.log(request.url().toString(), "");
                        logger.log(request.url().toString(), "Couldn't decode the response body; charset is likely malformed.");
                        logger.log(request.url().toString(), "<-- END HTTP");

                        sb.append("").append("\n");
                        sb.append("Couldn't decode the response body; charset is likely malformed.").append("\n");
                        sb.append("<-- END HTTP").append("\n");
                        postParam = "";
                        headerParam = "";
                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {

                    logger.log(request.url().toString(), "");
                    logger.log(request.url().toString(), "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");

                    sb.append("").append("\n");
                    sb.append("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)").append("\n");

                    logger.log2(request.url().toString(), "(binary " + buffer.size() + "-byte body omitted)", postParam, headerParam);

                } else {
                    if (contentLength != 0) {
                        logger.log(request.url().toString(), "");
                        logger.log(request.url().toString(), buffer.clone().readString(charset));

                        sb.append(request.url().toString()).append("\n");
                        sb.append(buffer.clone().readString(charset)).append("\n");
                        //TODO 响应结果
                        logger.log2(request.url().toString(), buffer.clone().readString(charset), postParam, headerParam);
                    }

                    logger.log(request.url().toString(), "<-- END HTTP (" + buffer.size() + "-byte body)");

                    sb.append("<-- END HTTP (" + buffer.size() + "-byte body)").append("\n");
                }
            }
        }

        logger.log3(request.url().toString(), sb.toString());
        postParam = "";
        headerParam = "";
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
