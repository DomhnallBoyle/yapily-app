package com.example.yapily_app.models;

import com.google.gson.annotations.SerializedName;

public class ConnectedResponse {

    @SerializedName("connected")
    private boolean isConnected;

    public boolean getIsConnected() {
        return this.isConnected;
    }
}
