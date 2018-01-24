package com.supets.pet.mock.ui.server;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.supets.pet.mockui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MainJsonActivity extends AppCompatActivity {
    private AsyncHttpServer server = new AsyncHttpServer();
    private AsyncServer mAsyncServer = new AsyncServer();
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        setContentView(webView);

        webView.loadUrl("http://localhost:54321");

        server.get("/", (request, response) -> {
            try {
                response.send(getIndexContent("index.html"));
            } catch (IOException e) {
                e.printStackTrace();
                response.code(500).end();
            }
        });

        server.get("/jquery-1.7.2.min.js", (request, response) -> {
            try {
                String fullPath = request.getPath();
                fullPath = fullPath.replace("%20", " ");
                String resourceName = fullPath;
                if (resourceName.startsWith("/")) {
                    resourceName = resourceName.substring(1);
                }
                if (resourceName.indexOf("?") > 0) {
                    resourceName = resourceName.substring(0, resourceName.indexOf("?"));
                }
                response.setContentType("application/javascript");
                BufferedInputStream bInputStream = new BufferedInputStream(getAssets().open(resourceName));
                response.sendStream(bInputStream, bInputStream.available());
            } catch (IOException e) {
                e.printStackTrace();
                response.code(404).end();
                return;
            }
        });

        server.get("/files/.*", (request, response) -> {
            String path = request.getPath().replace("/files/", "");
            try {
                path = URLDecoder.decode(path, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    response.sendStream(fis, fis.available());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            response.code(404).send("Not found!");
        });


        server.get("/files", (request, response) -> {
            String format=request.getQuery().getString("format");
            JSONArray array = new JSONArray();
            File dir = new File(Environment.getExternalStorageDirectory().getPath());
            getfiles(array, dir, format);
            response.send(array.toString());
        });

        server.listen(mAsyncServer, 54321);

    }

    private void getfiles(JSONArray array, File dir, String format) {

        if (TextUtils.isEmpty(format)) {
            return;
        }

        String[] fileNames = dir.list();
        if (fileNames != null) {
            for (String fileName : fileNames) {
                File file = new File(dir, fileName);

                if (file.exists() && file.isFile() && file.getName().endsWith("." + format)) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", fileName);
                        jsonObject.put("path", file.getAbsolutePath());
                        array.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (file.exists() && file.isDirectory()) {
                    getfiles(array, file, format);
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (server != null) {
            server.stop();
        }
        if (mAsyncServer != null) {
            mAsyncServer.stop();
        }
    }

    private String getIndexContent(String name) throws IOException {
        BufferedInputStream bInputStream = null;
        try {
            bInputStream = new BufferedInputStream(getAssets().open(name));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] tmp = new byte[10240];
            while ((len = bInputStream.read(tmp)) > 0) {
                baos.write(tmp, 0, len);
            }
            return new String(baos.toByteArray(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (bInputStream != null) {
                try {
                    bInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}