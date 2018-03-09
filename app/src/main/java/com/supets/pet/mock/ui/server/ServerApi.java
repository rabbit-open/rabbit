package com.supets.pet.mock.ui.server;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.supets.pet.mock.bean.LocalMockData;
import com.supets.pet.mock.config.Config;
import com.supets.pet.mock.dao.LocalMockDataDB;
import com.supets.pet.mock.dao.MockDataDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


public class ServerApi {

    private AsyncHttpServer server;
    private AsyncServer mAsyncServer;
    private Context mContext;

    public ServerApi(Context context, AsyncHttpServer server, AsyncServer mAsyncServer) {
        this.mContext = context;
        this.server = server;
        this.mAsyncServer = mAsyncServer;

        addhtml("/mp4", "mp4.html");
        addhtml("/jpg", "jpg.html");
        addhtml("/apk", "apk.html");
        addhtml("/tools", "tools.html");

        addhtml("/", "index.html");
        addhtml("/content.html", "content.html");

        addhtml("/readapidata.html", "readapidata.html");
        addhtml("/mockconfig.html*", "mockconfig.html");
        addLocalJSResource("/jquery-1.7.2.min.js");
        addLocalFileResource();
        addJsonApi();
        getMockData();
        getMockrealData();
        getMockUIData();
        savemockuidata();
        saveconfigdata();
        addmockapi();
        getmockconfig();
        this.server.listen(this.mAsyncServer, 54321);
    }


    private void addhtml(String path, String name) {
        server.get(path, (request, response) -> {
            try {
                response.send(ServerUtils.getIndexContent(mContext, name));
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

            if ("apk".equals(format)) {
                JSONArray apks = ServerUtils.getApk(mContext);
                if (apks != null) {
                    for (int i = 0; i < apks.length(); i++) {
                        try {
                            array.put(apks.get(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            response.send(array.toString());
        });
    }

    private void getMockData() {
        server.get("/getmockdata", (request, response) -> {
            String format = request.getQuery().getString("api");
            if (TextUtils.isEmpty(format)) {
                response.send(new Gson().toJson(MockDataDB.queryAllPage(0)));
            } else {
                response.send(new Gson().toJson(MockDataDB.queryAllMockDataPage(format, 0)));
            }
        });
    }

    private void getMockrealData() {
        server.get("/getmockrealdata", (request, response) -> {
            response.send(new Gson().toJson(MockDataDB.queryNewAllPage(1)));
        });
    }

    private void getMockUIData() {
        server.get("/getmockuidata", (request, response) -> {

            List<String> datas = MockDataDB.queryAllUrl();
            if (datas != null) {
                for (String temp : datas) {
                    List<LocalMockData> data = LocalMockDataDB.queryAllMockData(temp);
                    if (data == null || data.size() == 0) {
                        LocalMockDataDB.insertMockData(new LocalMockData(null, temp, null, false));
                    }
                }
            }

            String apikey = request.getQuery().getString("apikey");
            if (TextUtils.isEmpty(apikey)) {
                response.send(new Gson().toJson(LocalMockDataDB.queryAll()));
            } else {
                response.send(new Gson().toJson(LocalMockDataDB.queryAllMockData(apikey)));
            }

        });
    }

    private void savemockuidata() {
        server.get("/savemockuidata", (request, response) -> {

            // String id = request.getQuery().getString("id");
            String url = request.getQuery().getString("url");
            String checked = request.getQuery().getString("checked");
            String data = request.getQuery().getString("data");

            Log.v("serverapi", url);
            Log.v("serverapi", checked);
            Log.v("serverapi", data);

            List<LocalMockData> list = LocalMockDataDB.queryAllMockData(url);

            if (list != null && !list.isEmpty()) {
                LocalMockData mockData = list.get(0);
                mockData.setData(data);
                mockData.setSelected(Boolean.parseBoolean(checked));
                LocalMockDataDB.updateMockData(mockData);
            } else {
                LocalMockData mockData = new LocalMockData();
                mockData.setData(data);
                mockData.setUrl(url);
                mockData.setSelected(Boolean.parseBoolean(checked));
                LocalMockDataDB.insertMockData(mockData);
            }

            response.send("true");

        });
    }

    private void saveconfigdata() {
        server.get("/saveconfigdata", (request, response) -> {

            // String id = request.getQuery().getString("id");
            String apikey = request.getQuery().getString("apikey");
            String JsonSwitch = request.getQuery().getString("JsonSwitch");
            String DebugMode = request.getQuery().getString("DebugMode");
            String ToastSwitch = request.getQuery().getString("ToastSwitch");

            Log.v("serverapi", ToastSwitch);
            Log.v("serverapi", DebugMode);
            Log.v("serverapi", JsonSwitch);
            Log.v("serverapi", apikey);

            Config.setBaseAPI(apikey);
            Config.setJsonSwitch(Boolean.parseBoolean(JsonSwitch));
            Config.setDebugMode(Boolean.parseBoolean(DebugMode));
            Config.setToastSwitch(Integer.parseInt(ToastSwitch) == 1);

            response.send("true");

        });
    }

    private void addmockapi() {
        server.get("/addmockapi", (request, response) -> {

            // String id = request.getQuery().getString("id");
            String url = request.getQuery().getString("apikey");
            String data = request.getQuery().getString("data");

            Log.v("serverapi", data);
            Log.v("serverapi", url);

            List<LocalMockData> list = LocalMockDataDB.queryAllMockData(url);

            if (list != null && !list.isEmpty()) {
                LocalMockData mockData = list.get(0);
                mockData.setData(data);
                LocalMockDataDB.updateMockData(mockData);
            } else {
                LocalMockData mockData = new LocalMockData();
                mockData.setData(data);
                mockData.setUrl(url);
                mockData.setSelected(false);
                LocalMockDataDB.insertMockData(mockData);
            }

            response.send("true");

        });
    }

    private void getmockconfig() {
        server.get("/getmockconfig", (request, response) -> {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("apikey", Config.getBaseAPI());
                jsonObject.put("JsonSwitch", Config.getJsonSwitch());
                jsonObject.put("DebugMode", Config.getDebugMode());
                jsonObject.put("ToastSwitch", Config.getToastSwitch());
                response.send(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

}
