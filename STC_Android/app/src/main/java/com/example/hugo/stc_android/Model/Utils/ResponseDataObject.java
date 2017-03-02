package com.example.hugo.stc_android.Model.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Hugo on 14-07-2015.
 */
public class ResponseDataObject {
    private int statusCode;
    private List<String> responseData;
    private List<LinkedHashMap<String,String>> records;

    public ResponseDataObject() {
        responseData = new ArrayList<>();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<String> responseData) {
        this.responseData = responseData;
    }

    public List<LinkedHashMap<String, String>> getRecords() {
        return records;
    }

    public void setRecords(List<LinkedHashMap<String, String>> registos) {
        this.records = registos;
    }
}
