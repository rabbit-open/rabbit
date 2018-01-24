package com.supets.pet.mock.ui.server;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;

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


public class ServerApi {

    private AsyncHttpServer server;
    private AsyncServer mAsyncServer;
    private Context mContext;

    public ServerApi(Context context, AsyncHttpServer server, AsyncServer mAsyncServer) {
        this.mContext = context;
        this.server = server;
        this.mAsyncServer = mAsyncServer;
        addHome();
        addLocalJSResource("/jquery-1.7.2.min.js");
        addLocalFileResource();
        addJsonApi();
        this.server.listen(this.mAsyncServer, 54321);
    }

    private void addHome() {
        server.get("/", (request, response) -> {
            try {
                response.send(ServerUtils.getIndexContent(mContext, "index.html"));
            } catch (IOException e) {
                e.printStackTrace();
                response.code(500).end();
            }
        });
    }

    private void addLocalFileResource() {
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
    }

    private void addLocalJSResource(String path) {
        server.get(path, (request, response) -> {
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
                BufferedInputStream bInputStream = new BufferedInputStream(mContext.getAssets().open(resourceName));
                response.sendStream(bInputStream, bInputStream.available());
            } catch (IOException e) {
                e.printStackTrace();
                response.code(404).end();
                return;
            }
        });
    }

    private void addJsonApi() {
        server.get("/files", (request, response) -> {
            String format = request.getQuery().getString("format");
            JSONArray array = new JSONArray();
            File dir = new File(Environment.getExternalStorageDirectory().getPath());
            ServerUtils.getfiles(array, dir, format);
            response.send(array.toString());
        });
    }


}
