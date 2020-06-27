package com.example.yapily_app.models;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("id")
    private String applicationUserId;

    @SerializedName("token")
    private String token;

    public Login() {

    }

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
