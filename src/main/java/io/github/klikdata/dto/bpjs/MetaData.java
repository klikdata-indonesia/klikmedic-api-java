package io.github.klikdata.dto.bpjs;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class MetaData {
    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private Integer code;

    public MetaData() {
    }

    public MetaData(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static MetaData fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, MetaData.class);
    }
}
