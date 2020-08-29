package com.supets.pet.mocklib.widget;

public class MockData {

    private String url;
    private String requestParam;
    private String data;
    private String headerParam;
    private String responseParam;

    public MockData() {
    }

    public String getHeaderParam() {
        return headerParam;
    }

    public void setHeaderParam(String headerParam) {
        this.headerParam = headerParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRequestParam() {
        return this.requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

}
